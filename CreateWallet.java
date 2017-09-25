import java.io.File;
import java.io.IOException;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

public class CreateWallet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		createWallet();
	}

	/**
	 * this method creates a wallet with 5 transactions and save it to a local
	 * file
	 * 
	 * 
	 */
	public static void createWallet() {

		final NetworkParameters netParams = TestNet3Params.get();
		Wallet wallet = new Wallet(netParams);
		DeterministicSeed seed = wallet.getKeyChainSeed();

		System.out.println(" seed " + seed);

		// add some coin to this wallet
		wallet.getBalance().add(Coin.parseCoin("1000"));

		// add 5 transactions
		for (int i = 0; i < 5; i++) {
			wallet.addKey(new ECKey());
		}

		// save these transaction to a local file
		final File walletFile = new File("test.wallet");
		try {
			wallet.saveToFile(walletFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
