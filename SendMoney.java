import java.io.File;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.Wallet.BalanceType;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class SendMoney {

	public static void main(String[] args) {

		sendToAddress();
	}

	/**
	 * this method sends from one wallet to another
	 */
	public static void sendToAddress() {

		final NetworkParameters netParams = TestNet3Params.get();
		// create two wallets

		// sender
		Wallet sender = new Wallet(netParams);
		System.out.println(sender);
		System.out.println(sender.currentReceiveAddress());

		// add some coin
		sender.getBalance().add(Coin.parseCoin("1000"));

		// receiver
		Wallet receiver = new Wallet(netParams);
		System.out.println(receiver.currentChangeAddress());

		// sending 100 coins to this receiver
		Address to = Address.fromBase58(netParams, receiver.currentReceiveAddress().toBase58());

		WalletAppKit kit = new WalletAppKit(netParams, new File("."), "wallet");

		kit.startAsync();
		kit.awaitRunning();

		Coin value = Coin.parseCoin("0.09");

		try {

			Wallet.SendResult result = kit.wallet().sendCoins(kit.peerGroup(), to, value);
			System.out.println("coins sent. transaction hash: " + result.tx.getHashAsString());
		} catch (InsufficientMoneyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			ListenableFuture<Coin> balanceFuture = kit.wallet().getBalanceFuture(value, BalanceType.AVAILABLE);
			FutureCallback<Coin> callback = new FutureCallback<Coin>() {
				@Override
				public void onSuccess(Coin balance) {
					System.out.println("coins arrived and the wallet now has enough balance");
				}

				@Override
				public void onFailure(Throwable t) {
					System.out.println("something went wrong");
				}
			};
			Futures.addCallback(balanceFuture, callback);

		}
	}
}
