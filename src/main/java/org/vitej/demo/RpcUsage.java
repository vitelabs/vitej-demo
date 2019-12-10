package org.vitej.demo;

import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.*;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.ContractUtils;
import org.vitej.core.utils.ProtocolUtils;
import org.vitej.core.utils.abi.Abi;
import org.vitej.core.wallet.KeyPair;
import org.vitej.core.wallet.Wallet;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RpcUsage {
    public static void main(String[] args) throws Exception {
        // New vitej client
        {
            Vitej vitej = new Vitej(new HttpService());
            Vitej vitej1 = new Vitej(new HttpService("http://118.24.128.145:48132"));// TODO
            KeyPair keyPairDefault = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
            Vitej vitej2 = new Vitej(new HttpService("http://127.0.0.1:48132"), keyPairDefault);
        }

        // Sync call
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlocksResponse response = vitej.getAccountBlocksByAddress(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"), 0, 10
            ).send();
            List<AccountBlock> accountBlockList = response.getResult();
        }
        // Async call
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlocksResponse response = vitej.getAccountBlocksByAddress(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"), 0, 10
            ).sendAsync().get();
            List<AccountBlock> accountBlockList = response.getResult();
        }

        // Transfer request
        {
            Vitej vitej = new Vitej(new HttpService());
            KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
            Request<?, EmptyResponse> request = vitej.sendTransaction(keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CALL.getValue())
                            .setToAddress(new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"))
                            .setAmount(BigInteger.valueOf(1))
                            .setTokenId(CommonConstants.VITE_TOKEN_ID)
                            .setData("Hello".getBytes()),
                    true);
            Hash sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
            EmptyResponse response = request.send();
        }

        // Transfer response
        {
            Vitej vitej = new Vitej(new HttpService());
            KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
            Request<?, EmptyResponse> request = vitej.sendTransaction(keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.RECEIVE.getValue())
                            .setSendBlockHash(new Hash("ef5dccd73a6ef6370bc72b56b686362fd095152e2746f21113c2015e243b5056")),
                    true);
            Hash sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
            EmptyResponse response = request.send();
        }

        // Call contract request
        {
            Vitej vitej = new Vitej(new HttpService());
            KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
            Abi abi = Abi.fromJson("[{\"type\":\"function\",\"name\":\"VoteForSBP\", \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"}]}]");
            byte[] callContractData = abi.encodeFunction("VoteForSBP", "Vite_SBP01");
            Request<?, EmptyResponse> request = vitej.sendTransaction(
                    keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CALL.getValue())
                            .setToAddress(new Address("vite_0000000000000000000000000000000000000004d28108e76b"))
                            .setAmount(new BigInteger("0"))
                            .setTokenId(CommonConstants.VITE_TOKEN_ID)
                            .setData(callContractData),
                    true
            );
            Hash sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
            EmptyResponse response = request.send();
            boolean callSuccess = ProtocolUtils.checkCallContractResult(vitej, sendBlockHash);
        }

        // Call create contract request
        {
            Vitej vitej = new Vitej(new HttpService());
            KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();
            byte[] bytecode = BytesUtils.hexStringToBytes("6080604052348015600f57600080fd5b50604051602080608183398101806040526020811015602d57600080fd5b810190808051906020019092919050505050603580604c6000396000f3fe6080604052600080fdfea165627a7a723058208602dc0b6a1bf2e56f2160299868dc8c3f435c9af6d384858722a21906c7c0740029");
            Abi abi = Abi.fromJson("[{\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]");
            byte[] callConstructorData = abi.encodeConstructor(BigInteger.valueOf(1));
            byte[] createContractData = ContractUtils.getCreateContractData(bytecode, callConstructorData, 2, 1, 10);
            Request<?, EmptyResponse> request = vitej.sendTransaction(
                    keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CREATE.getValue())
                            .setAmount(new BigInteger("0"))
                            .setTokenId(CommonConstants.VITE_TOKEN_ID)
                            .setFee(CommonConstants.CREATE_CONTRACT_FEE)
                            .setData(createContractData),
                    true
            );
            Hash sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
            EmptyResponse response = request.send();
            boolean callSuccess = ProtocolUtils.checkCallContractResult(vitej, sendBlockHash);
        }

        // Get required quota for transaction
        {
            Vitej vitej = new Vitej(new HttpService());
            RequiredQuotaResponse response = vitej.getRequiredQuota(
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CALL.getValue())
                            .setAddress(new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"))
                            .setToAddress(new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"))
                            .setData("Hello".getBytes())).send();
            Long requiredQuota = response.getRequiredQuota();
        }

        // Get PoW difficulty to send a transaction
        {
            Vitej vitej = new Vitej(new HttpService());
            PoWDifficultyResponse response = vitej.getPoWDifficulty(
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CALL.getValue())
                            .setAddress(new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"))
                            .setToAddress(new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"))
                            .setData("Hello".getBytes())).send();
            Long requiredQuota = response.getResult().getRequiredQuota();
            boolean isCongested = response.getResult().getCongestion();
            BigInteger difficulty = response.getResult().getDifficulty();
        }

        // Calculate PoW, get PoW nonce
        {
            Vitej vitej = new Vitej(new HttpService());
            PoWNonceResponse response = vitej.getPoWNonce(
                    BigInteger.valueOf(67108863),
                    new Hash("d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95")
            ).send();
            byte[] nonce = response.getNonce();
        }

        // Get account blocks by address
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlocksResponse response = vitej.getAccountBlocksByAddress(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"),
                    0,
                    10).send();
            List<AccountBlock> accountBlockList = response.getResult();
        }

        // Get account block by address and height
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlockResponse response = vitej.getAccountBlockByHeight(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"),
                    1L).send();
            AccountBlock accountBlock = response.getResult();
        }

        // Get account block by hash
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlockResponse response = vitej.getAccountBlockByHash(
                    new Hash("c4b11ff481c5476945000993816794fbc21a315901aaecb523b503c19c133154")).send();
            AccountBlock accountBlock = response.getResult();
        }

        // Get latest account block by address
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlockResponse response = vitej.getLatestAccountBlock(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd")).send();
            AccountBlock accountBlock = response.getResult();
        }

        // Batch query account blocks by token id
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlocksResponse response = vitej.getAccountBlocks(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"),
                    new Hash("c4b11ff481c5476945000993816794fbc21a315901aaecb523b503c19c133154"),
                    CommonConstants.VITE_TOKEN_ID,
                    10
            ).send();
            List<AccountBlock> accountBlockList = response.getResult();
        }

        // Get account block count and balance by address
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountInfoResponse response = vitej.getAccountInfoByAddress(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd")
            ).send();
            Long blockCount = response.getResult().getBlockCount();
            Map<TokenId, AccountInfoResponse.BalanceInfo> balanceInfoMap = response.getResult().getBalanceInfoMap();

        }

        // Get unreceived blocks by address
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountBlocksResponse response = vitej.getUnreceivedBlocksByAddress(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"),
                    0,
                    10
            ).send();
            List<AccountBlock> accountBlockList = response.getResult();
        }

        // Get unreceived block count and balance by address
        {
            Vitej vitej = new Vitej(new HttpService());
            AccountInfoResponse response = vitej.getUnreceivedTransactionSummaryByAddress(
                    new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68")
            ).send();
            Long blockCount = response.getResult().getBlockCount();
            Map<TokenId, AccountInfoResponse.BalanceInfo> balanceInfoMap = response.getResult().getBalanceInfoMap();
        }

        // Get latest snapshot block hash
        {
            Vitej vitej = new Vitej(new HttpService());
            LatestSnapshotHashResponse response = vitej.getLatestSnapshotHash().send();
            Hash latestSnapshotHash = response.getHash();
        }

        // Get latest snapshot block height
        {
            Vitej vitej = new Vitej(new HttpService());
            SnapshotChainHeightResponse response = vitej.getSnapshotChainHeight().send();
            Long latestSnapshotHeight = response.getHeight();
        }

        // Get latest snapshot block
        {
            Vitej vitej = new Vitej(new HttpService());
            SnapshotBlockResponse response = vitej.getLatestSnapshotBlock().send();
            SnapshotBlock snapshotBlock = response.getResult();
        }

        // Batch query snapshot blocks
        {
            Vitej vitej = new Vitej(new HttpService());
            SnapshotBlocksResponse response = vitej.getSnapshotBlocks(
                    100L,
                    10
            ).send();
            List<SnapshotBlock> snapshotBlock = response.getResult();
        }

        // Get vm events by contract account block hash
        {
            Vitej vitej = new Vitej(new HttpService());
            VmlogsResponse response = vitej.getVmlogs(
                    new Hash("d519bd49599df00b6a5992a50065af7945c4b6af269af8791cca5688f3277e37")
            ).send();
            List<Vmlog> vmLogList = response.getResult();
        }

        // Batch query vm events by contract address and height range and topics
        {
            Vitej vitej = new Vitej(new HttpService());
            VmLogFilter filter = new VmLogFilter(new Address("vite_000000000000000000000000000000000000000595292d996d"),
                    1L, 10L);
            filter.setTopics(Arrays.asList(
                    Collections.emptyList(),
                    Arrays.asList(new Hash("000000000000000000000000000000000000000000005649544520544f4b454e"), new Hash("00000000000000000000000000000000000000000000564954455820434f494e")),
                    Collections.emptyList()
            ));
            VmlogInfosResponse response = vitej.getVmlogsByFilter(
                    filter
            ).send();
            List<VmLogInfo> vmLogInfoList = response.getResult();
        }

        // Get contract info by contract address
        {
            Vitej vitej = new Vitej(new HttpService());
            ContractInfoResponse response = vitej.getContractInfo(
                    new Address("vite_000000000000000000000000000000000000000595292d996d")
            ).send();
            ContractInfo contractInfo = response.getResult();
        }

        // Call contract offchain method
        {
            Vitej vitej = new Vitej(new HttpService());
            Abi abi = Abi.fromJson("[{\"inputs\":[],\"name\":\"getData\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"type\":\"offchain\"}]");
            String methodName = "getData";
            CallOffChainMethodResponse response = vitej.callOffChainMethod(
                    new Address("vite_da0e4189f8155035d5b373f8f1328e43d7d70980f4fb69ff18"),
                    BytesUtils.hexStringToBytes("6080604052600436106042576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063c1a34865146044576042565b005b604a6060565b6040518082815260200191505060405180910390f35b60006000600050549050606e565b9056fea165627a7a7230582098acc939ef119097e24d6b599d9dd18bb2061a9fab6ec77401def1c0a7e52ecd0029"),
                    abi.encodeOffchain(methodName)
            ).send();
            List<?> outputList = abi.decodeOffchainOutput(methodName, response.getReturnData());
            BigInteger output = ((BigInteger) outputList.get(0));
        }

        // Get quota by address
        {
            Vitej vitej = new Vitej(new HttpService());
            QuotaResponse response = vitej.getQuotaByAccount(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd")
            ).send();
            Long currentQuota = response.getResult().getCurrentQuota();
            Long maxQuota = response.getResult().getMaxQuota();
            BigInteger stakeAmount = response.getResult().getStakeAmount();
        }

        // Get stake info list by address
        {
            Vitej vitej = new Vitej(new HttpService());
            StakeListResponse response = vitej.getStakeList(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"),
                    0,
                    10
            ).send();
            BigInteger totalStakeAmount = response.getResult().getTotalStakeAmount();
            Integer totalStakeCount = response.getResult().getTotalStakeCount();
            List<StakeListResponse.StakeInfo> stakeInfoList = response.getResult().getStakeList();
        }

        // Calculate required stake amount by quota used per snapshot block
        {
            Vitej vitej = new Vitej(new HttpService());
            StakeAmountResponse response = vitej.getRequiredStakeAmount(21000L).send();
            BigInteger stakeAmount = response.getStakeAmount();
        }

        // Get sbp info list by address
        {
            Vitej vitej = new Vitej(new HttpService());
            SBPListResponse response = vitej.getSBPList(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd")
            ).send();
            List<SBPInfo> sbpInfoList = response.getResult();
        }

        // Get sbp reward pending withdrawal by sbp name
        {
            Vitej vitej = new Vitej(new HttpService());
            SBPRewardResponse response = vitej.getSBPRewardPendingWithdrawal(
                    "s1"
            ).send();
            SBPRewardResponse.Result reward = response.getResult();
        }

        // Get sbp info by name
        {
            Vitej vitej = new Vitej(new HttpService());
            SBPResponse response = vitej.getSBP(
                    "s1"
            ).send();
            SBPInfo sbpInfo = response.getResult();
        }

        // Get sbp reward by cycle
        {
            Vitej vitej = new Vitej(new HttpService());
            SBPRewardDetailResponse response = vitej.getSBPRewardByCycle(
                    0L
            ).send();
            SBPRewardDetailResponse.Result rewardDetail = response.getResult();
        }

        // Get sbp vote details by cycle
        {
            Vitej vitej = new Vitej(new HttpService());
            SBPVoteDetailsResponse response = vitej.getSBPVoteDetailsByCycle(
                    0L
            ).send();
            List<SBPVoteDetailsResponse.Result> voteDetail = response.getResult();
        }

        // Get voted sbp by address
        {
            Vitej vitej = new Vitej(new HttpService());
            VotedSBPResponse response = vitej.getVotedSBP(
                    new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd")
            ).send();
            VotedSBPResponse.Result votedSBP = response.getResult();
        }

        // Get token info list
        {
            Vitej vitej = new Vitej(new HttpService());
            TokenInfoListWithTotalResponse response = vitej.getTokenInfoList(
                    0,
                    10
            ).send();
            Integer count = response.getResult().getTotalCount();
            List<TokenInfo> tokenInfoList = response.getResult().getTokenInfoList();
        }

        // Get token info by id
        {
            Vitej vitej = new Vitej(new HttpService());
            TokenInfoResponse response = vitej.getTokenInfoById(
                    new TokenId("tti_5649544520544f4b454e6e40")
            ).send();
            TokenInfo tokenInfo = response.getResult();
        }

        // Get token info list by owner
        {
            Vitej vitej = new Vitej(new HttpService());
            TokenInfoListResponse response = vitej.getTokenInfoListByOwner(
                    new Address("vite_bb6ad02107a4422d6a324fd2e3707ad53cfed9359378a78792")
            ).send();
            List<TokenInfo> tokenInfo = response.getResult();
        }

        // Get net node info
        {
            Vitej vitej = new Vitej(new HttpService());
            NetNodeInfoResponse response = vitej.netNodeInfo().send();
            NetNodeInfoResponse.Result nodeInfo = response.getResult();
        }

        // Get net sync info
        {
            Vitej vitej = new Vitej(new HttpService());
            NetSyncInfoResponse response = vitej.netSyncInfo().send();
            NetSyncInfoResponse.Result syncInfo = response.getResult();
        }

        // Get net sync detail
        {
            Vitej vitej = new Vitej(new HttpService());
            NetSyncDetailResponse response = vitej.netSyncDetail().send();
            NetSyncDetailResponse.Result syncDetail = response.getResult();
        }
    }
}
