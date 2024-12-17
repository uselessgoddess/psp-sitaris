package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Transfer;
import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.TransferRepository;

import java.sql.SQLException;

public class TransferService extends BaseService {
	private AccountRepository accountRepository;
	private TransferRepository transferRepository;

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void setTransferRepository(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}

	public void withdrawCash(String accountNumber, Long sum) throws SQLException {

	}

	public void depositCash(String accountNumber, Long sum) throws SQLException {

	}

	public void transfer(String senderNumber, String receiverNumber, Long sum, String purpose) throws SQLException {

	}
}
