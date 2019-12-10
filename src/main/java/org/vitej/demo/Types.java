package org.vitej.demo;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.utils.BytesUtils;

public class Types {
    public static void main(String[] args) {
        // Address
        Address address = new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd");
        Address address1 = Address.stringToAddress("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd");
        // Check address type
        boolean isUser = address.isUser();
        boolean isContact = address.isContract();

        // Token id
        TokenId tokenId = new TokenId("tti_5649544520544f4b454e6e40");
        TokenId tokenId1 = TokenId.stringToTokenId("tti_5649544520544f4b454e6e40");

        // Hash
        Hash hash = new Hash("7683bbc8be1391172ed21cc1fe0843ac3b1311109aa329601b73f717e6a93b53");
        Hash hash1 = Hash.stringToHash("7683bbc8be1391172ed21cc1fe0843ac3b1311109aa329601b73f717e6a93b53");
        // Convert data to hash
        byte[] data = BytesUtils.hexStringToBytes("7683bbc8be1391172ed21cc1fe0843ac3b1311109aa329601b73f717e6a93b53");
        Hash dataHash = Hash.dataToHash(data);
    }
}
