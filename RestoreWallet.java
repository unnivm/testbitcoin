import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

public class RestoreWallet {

	public static void main(String[] arg) {
		
		 String seedCode = "endorse, smart, left, strategy, creek, tortoise, party, anger, aim, gossip, post, pulp";
		 String passphrase = "";
	     Long creationtime = 1409478661L;
	        
	     restoreWallet(seedCode, passphrase, creationtime);   
	}
	
	/**
	 * this method restores a wallet based on seed 
	 * Mnemonic standard :https://github.com/bitcoin/bips/blob/master/bip-0039/english.txt
	 * @param seedCode
	 * @param passphrase
	 * @param creattionTime
	 */
	public static void restoreWallet(String seedCode, String passphrase, long creattionTime) {

		NetworkParameters params = TestNet3Params.get();
		try {
			DeterministicSeed seed = new DeterministicSeed(seedCode, null, passphrase, creattionTime);
			Wallet wallet = Wallet.fromSeed(params, seed);
			
			// print the contents of the wallets
			System.out.println(wallet.toString());
			
			System.out.println(" Balance: " + wallet.getBalance().value);
			System.out.println(" wallet.currentReceiveAddress() : " + wallet.currentReceiveAddress());
		} catch (UnreadableWalletException e) {
			e.printStackTrace();
		}

	}
}
