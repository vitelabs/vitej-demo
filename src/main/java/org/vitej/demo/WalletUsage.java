package org.vitej.demo;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.wallet.Crypto;
import org.vitej.core.wallet.KeyPair;
import org.vitej.core.wallet.Wallet;

import java.util.Arrays;
import java.util.List;

public class WalletUsage {
    public static void main(String[] args) {
        // New wallet
        Wallet wallet = new Wallet();
        // New wallet from mnemonic
        Wallet wallet1 = new Wallet(Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable"));
        // Get mnemonic
        List<String> mnemonic = wallet.getMnemonic();
        // Derive key pair from wallet
        // wallet.deriveKeyPair() equals to wallet.deriveKeyPair(0)
        KeyPair keyPair = wallet.deriveKeyPair();
        KeyPair keyPair1 = wallet.deriveKeyPair(1);

        // Get public key, private key, address from key pair
        byte[] publicKey = keyPair.getPublicKey();
        byte[] privateKey = keyPair.getPrivateKey();
        Address address = keyPair.getAddress();

        // Sign message
        byte[] message = BytesUtils.hexStringToBytes("");
        byte[] signedData = keyPair.sign(message);
        // Verify signed message with public key
        boolean verified = Crypto.verify(signedData, message, keyPair.getPublicKey());
    }
}
