package org.vitej.demo;

import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.response.AccountBlock;
import org.vitej.core.utils.BlockUtils;
import org.vitej.core.utils.ProtocolUtils;

public class Utils {
    public static void main(String[] args) throws Exception {
        // Check block type
        Boolean isSendBlock = BlockUtils.isSendBlock(EBlockType.SEND_CALL.getValue());
        Boolean isReceiveBlock = BlockUtils.isReceiveBlock(EBlockType.RECEIVE.getValue());
        AccountBlock accountBlock = new AccountBlock();
        Boolean isSendBlock1 = accountBlock.isSendBlock();
        Boolean isReceiveBlock1 = accountBlock.isReceiveBlock();

        // Check call contract result
        Vitej vitej = new Vitej(new HttpService());
        Hash sendBlockHash = new Hash("7683bbc8be1391172ed21cc1fe0843ac3b1311109aa329601b73f717e6a93b53");
        boolean success = ProtocolUtils.checkCallContractResult(vitej, sendBlockHash);
        boolean success1 = ProtocolUtils.checkCallContractResult(vitej, sendBlockHash, 10);
    }
}
