package org.vitej.demo;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.abi.Abi;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AbiUtils {
    public static void main(String[] args) throws IOException {
        {
            // Parse abi json string
            Abi abi = Abi.fromJson("[" +
                    "{\"type\":\"function\",\"name\":\"voteForSBP\", \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"}]}," +
                    "{\"type\":\"offchain\",\"name\":\"getVotes\", \"inputs\":[{\"name\":\"voteAddress\",\"type\":\"address\"}], \"outputs\":[{\"name\":\"sbpName\",\"type\":\"string\"}]}," +
                    "{\"type\":\"event\",\"name\":\"VoteForSBP\", \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"},{\"name\":\"voteAddress\",\"type\":\"address\"}]}" +
                    "]");
            // Find function by name
            Abi.Function functionByName = abi.findFunctionByName("voteForSBP");
            // Encode call function data
            byte[] encodedFunctionData1 = functionByName.encode("Vite_SBP01");
            byte[] encodedFunctionData2 = abi.encodeFunction("voteForSBP", "Vite_SBP01");
            // Find function by encoded data
            Abi.Function functionByData = abi.findFunctionByData(encodedFunctionData1);
            // Decode call function data
            List<?> decodedFunctionParams = functionByData.decode(encodedFunctionData1);
            List<?> decodedFunctionParams2 = abi.decodeFunction(encodedFunctionData1);

            // Find offchain function by name
            Abi.Offchain offchainByName = abi.findOffchainByName("getVotes");
            // Encode call offchain function data
            byte[] encodedOffchainData1 = offchainByName.encode(new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"));
            byte[] encodedOffchainData2 = abi.encodeOffchain("getVotes", new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"));
            // Decode offchain function output params
            List<?> decodedOffchainParams = abi.decodeOffchainOutput("getVotes", BytesUtils.hexStringToBytes("0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000a566974655f534250303100000000000000000000000000000000000000000000"));

            // Find event by name
            Abi.Event eventByName = abi.findEventByName("VoteForSBP");
            // Find event by topics
            List<Hash> eventTopics = Arrays.asList(new Hash("afa4799f2c9e07964e722c02e1c5b6f1a84aca56854e5b0eba69c2a067843cd1"));
            Abi.Event eventByTopics = abi.findEventByTopics(eventTopics);
            // Decode event params
            byte[] eventData = BytesUtils.hexStringToBytes("000000000000000000000000000000000000000000000000000000000000004000000000000000000000000996e651f3885e6e6b83dfba8caa095ff7aa248e00000000000000000000000000000000000000000000000000000000000000000a566974655f534250303100000000000000000000000000000000000000000000");
            List<?> decodedEventParams1 = eventByTopics.decode(eventData, eventTopics);
            List<?> decodedEventParams2 = abi.decodeEvent(eventData, eventTopics);
        }
    }
}
