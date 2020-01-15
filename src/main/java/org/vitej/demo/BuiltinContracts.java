package org.vitej.demo;

import com.google.common.base.Preconditions;
import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.request.IssueTokenParams;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.response.EmptyResponse;
import org.vitej.core.utils.ProtocolUtils;
import org.vitej.core.wallet.KeyPair;
import org.vitej.core.wallet.Wallet;

import java.math.BigInteger;
import java.util.Arrays;

public class BuiltinContracts {
    public static void main(String[] args) {
        Vitej vitej = new Vitej(new HttpService());
        KeyPair keyPair = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable")).deriveKeyPair();

        // Stake for quota
        {
            try {
                Request<?, EmptyResponse> request = vitej.stakeForQuota(keyPair, keyPair.getAddress(), org.vitej.core.constants.BuiltinContracts.MINIMUM_STAKE_FOR_QUOTA_AMOUNT);
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Cancel quota staking
        {
            try {
                Request<?, EmptyResponse> request = vitej.cancelQuotaStaking(keyPair, new Hash("874aeae0389118ade5f81371041a45bb39a85630b3eb463c3329dfef89618d36"));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Register sbp
        {
            try {
                Request<?, EmptyResponse> request = vitej.registerSBP(keyPair, "test_sbp", keyPair.getAddress(), keyPair.getAddress());
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Update sbp block producing address
        {
            try {
                Request<?, EmptyResponse> request = vitej.updateSBPBlockProducingAddress(keyPair, "test_sbp", new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Update sbp reward withdraw address
        {
            try {
                Request<?, EmptyResponse> request = vitej.updateSBPRewardWithdrawAddress(keyPair, "test_sbp", new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Withdraw sbp reward
        {
            try {
                Request<?, EmptyResponse> request = vitej.withdrawSBPReward(keyPair, "test_sbp", new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Revoke sbp
        {
            try {
                Request<?, EmptyResponse> request = vitej.revokeSBP(keyPair, "test_sbp");
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Vote for sbp
        {
            try {
                Request<?, EmptyResponse> request = vitej.voteForSBP(keyPair, "s1");
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Cancel sbp voting
        {
            try {
                Request<?, EmptyResponse> request = vitej.cancelSBPVoting(keyPair);
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Issue token
        {
            try {
                Request<?, EmptyResponse> request = vitej.issueToken(keyPair,
                        new IssueTokenParams()
                                .setReIssuable(true)
                                .setTokenName("Test Token")
                                .setTokenSymbol("TT")
                                .setTotalSupply(BigInteger.valueOf(10000L))
                                .setMaxSupply(BigInteger.valueOf(20000L))
                                .setDecimals(1)
                                .setOwnerBurnOnly(false));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Reissue token
        {
            try {
                Request<?, EmptyResponse> request = vitej.reIssue(keyPair, new TokenId("tti_10b56995f5d6a6e1f9a60441"), BigInteger.valueOf(100), keyPair.getAddress());
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Burn token
        {
            try {
                Request<?, EmptyResponse> request = vitej.burn(keyPair, new TokenId("tti_10b56995f5d6a6e1f9a60441"), BigInteger.valueOf(100));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Transfer token ownership
        {
            try {
                Request<?, EmptyResponse> request = vitej.transferOwnership(keyPair, new TokenId("tti_10b56995f5d6a6e1f9a60441"), new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Disable reissue
        {
            try {
                Request<?, EmptyResponse> request = vitej.disableReIssue(keyPair, new TokenId("tti_10b56995f5d6a6e1f9a60441"));
                EmptyResponse response = request.send();
                Preconditions.checkArgument(response.getError() == null);
                Hash h = ((TransactionParams) request.getParams().get(0)).getHashRaw();
                Preconditions.checkArgument(ProtocolUtils.checkCallContractResult(vitej, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
