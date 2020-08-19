package org.vitej.demo;

import com.google.common.base.Preconditions;
import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.response.AccountBlock;
import org.vitej.core.protocol.methods.response.AccountBlockResponse;
import org.vitej.core.utils.BlockUtils;
import org.vitej.core.utils.ProtocolUtils;
import org.vitej.core.wallet.KeyPair;
import org.vitej.core.wallet.Wallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static void main(String[] args) throws Exception {
        checkBlockType();
        checkContractCallResult();

        signBlockOffline();
        deriveWalletAddress();
        getLatestTransaction();
    }

    // create a send transaction block from scratch and sign offline
    public static void signBlockOffline() {
        KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
        Preconditions.checkNotNull(keyPair, "wrong key pair");
        // create transaction block
        TransactionParams transaction = new TransactionParams()
                .setBlockType(EBlockType.SEND_CALL.getValue())
                .setToAddress(new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"))
                .setAmount(BigInteger.valueOf(1))
                .setTokenId(CommonConstants.VITE_TOKEN_ID)
                .setData("Hello".getBytes())
                .setPublicKey(keyPair.getPublicKey())
                .setAddress(keyPair.getAddress())
                .setHeight(12345L)
                .setPreviousHash(new Hash("7683bbc8be1391172ed21cc1fe0843ac3b1311109aa329601b73f717e6a93b53"))
                .setSendBlockHash(CommonConstants.EMPTY_HASH)
                .setFee(BigInteger.ZERO);
        // block hash
        transaction.setHash(BlockUtils.computeHash(transaction));
        // sign the block
        byte[] signature = BlockUtils.computeSigunature(keyPair, transaction);
        transaction.setSignature(signature);
    }

    // derive 10 addresses
    public static List<Address> deriveWalletAddress() {

        // create wallet from mnemonic phrase
        Wallet wallet = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable"));

        // derive 10 addresses
        ArrayList<Address> addresses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            KeyPair keyPair = wallet.deriveKeyPair(i);
            Address address = keyPair.getAddress();
            addresses.add(address);
            System.out.println("Address " + i + ": " + address);
        }
        return addresses;
    }


    // get the latest transaction of an account
    public static AccountBlock getLatestTransaction() throws IOException {

        Vitej vitej = new Vitej(new HttpService("http://167.71.46.82:48132")); // TODO - replace with your node
        AccountBlockResponse response = vitej.getLatestAccountBlock(
                new Address("vite_945a8f5d6870367551522fafccd4dbfe60d249b9287cf2d7ee")).send();
        AccountBlock accountBlock = response.getResult();
        System.out.println("From:" + accountBlock.getFromAddress());
        System.out.println("Address:" + accountBlock.getToAddress());
        System.out.println("Hash:" + accountBlock.getHash());
        System.out.println("Height:" + accountBlock.getHeight());
        System.out.println("Token:" + accountBlock.getTokenInfo().getTokenName());
        System.out.println("Amount:" + accountBlock.getAmount());
        System.out.println("Time:" + accountBlock.getTimestamp());
        return accountBlock;
    }

    // check block type
    public static void checkBlockType() {
        // Check block type
        Boolean isSendBlock = BlockUtils.isSendBlock(EBlockType.SEND_CALL.getValue());
        Boolean isReceiveBlock = BlockUtils.isReceiveBlock(EBlockType.RECEIVE.getValue());
        AccountBlock accountBlock = new AccountBlock();
        accountBlock.setBlockType(EBlockType.SEND_CALL.getValue());
        Boolean isSendBlock1 = accountBlock.isSendBlock();
        Boolean isReceiveBlock1 = accountBlock.isReceiveBlock();
    }

    // check contract call result
    public static void checkContractCallResult() throws IOException {
        // Check call contract result
        Vitej vitej = new Vitej(new HttpService("http://167.71.46.82:48132"));
        Hash sendBlockHash = new Hash("6ca1d4307d999cc0bb1b76d03059138b80f02c3997c2973405702d5ffb79907a");
        boolean success = ProtocolUtils.checkCallContractResult(vitej, sendBlockHash);
        boolean success1 = ProtocolUtils.checkCallContractResult(vitej, sendBlockHash, 10);
    }
}
