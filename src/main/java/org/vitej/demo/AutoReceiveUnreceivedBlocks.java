package org.vitej.demo;

import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.response.AccountBlock;
import org.vitej.core.protocol.methods.response.EmptyResponse;
import org.vitej.core.wallet.KeyPair;
import org.vitej.core.wallet.Wallet;

import java.util.Arrays;
import java.util.List;

public class AutoReceiveUnreceivedBlocks {
    public static void main(String[] args) {
        simpleAutoReceive();
    }


    /**
     * A simple way to automatically receive unreceived account blocks.
     * Try to receive 10 blocks in every second. If an error occurs during receiving, including but
     * not limited to network error, block chain fork, insufficient quota, sleep 1 second and retry.
     * <p>
     * This method uses a simplest way to receive an account block, which auto fills previous hash
     * and height fields by querying latest account block.
     */
    private static void simpleAutoReceive() {
        KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
        Vitej vitej = new Vitej(new HttpService(), keyPair);
        while (true) {
            try {
                List<AccountBlock> unreceivedList = vitej.getSelfUnreceivedBlocks(0, 10).send().getResult();
                for (AccountBlock accountBlock : unreceivedList) {
                    EmptyResponse response = vitej.selfSendTransaction(
                            new TransactionParams()
                                    .setBlockType(EBlockType.RECEIVE.getValue())
                                    .setSendBlockHash(accountBlock.getHash()),
                            false).send();
                    if (response.getError() != null) {
                        System.out.println(response.getError().toString());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * A quicker way to automatically receive unreceived account blocks.
     * Try to receive 10 blocks in every second. If an error occurs during receiving, including but
     * not limited to network error, block chain fork, insufficient quota, sleep 1 second and retry.
     * <p>
     * This method is quicker compared to simpleAutoReceive method. As it uses the last account block
     * to fill in the previous hash and height fields instead of querying the chain when sending
     * every transaction. The shortcoming of this method is that when account chain forks between
     * sending two transactions, the second transaction will be refused because of referring to a
     * non-existing previous hash or using a discontinuous block height.
     */
    private static void quickAutoReceive() {
        KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
        Vitej vitej = new Vitej(new HttpService(), keyPair);
        while (true) {
            try {
                AccountBlock latestAccountBlock = vitej.getSelfLatestAccountBlock().send().getResult();
                Hash previousHash = latestAccountBlock == null ? CommonConstants.EMPTY_HASH : latestAccountBlock.getHash();
                Long previousHeight = latestAccountBlock == null ? 0 : latestAccountBlock.getHeight();
                List<AccountBlock> unreceivedList = vitej.getSelfUnreceivedBlocks(0, 10).send().getResult();
                for (AccountBlock accountBlock : unreceivedList) {
                    Request<?, EmptyResponse> request = vitej.selfSendTransaction(
                            new TransactionParams()
                                    .setBlockType(EBlockType.RECEIVE.getValue())
                                    .setPreviousHash(previousHash)
                                    .setHeight(previousHeight + 1)
                                    .setSendBlockHash(accountBlock.getHash()),
                            false);
                    EmptyResponse response = request.send();
                    if (response.getError() != null) {
                        System.out.println(response.getError().toString());
                        break;
                    }
                    previousHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                    previousHeight = ((TransactionParams) request.getParams().get(0)).getHeightRaw();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
