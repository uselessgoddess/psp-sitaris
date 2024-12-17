package by.vsu.ist.service;

import by.vsu.ist.repository.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceContainer implements AutoCloseable {
	private AccountService accountService;
	public AccountService getAccountServiceInstance() throws SQLException {
		if(accountService == null) {
			accountService = new AccountService();
			accountService.setTransactionManager(getTransactionManagerInstance());
			accountService.setAccountRepository(getAccountRepositoryInstance());
			accountService.setTransferRepository(getTransferRepositoryInstance());
		}
		return accountService;
	}

	private TransferService transferService;
	public TransferService getTransferServiceInstance() throws SQLException {
		if(transferService == null) {
			transferService = new TransferService();
			transferService.setTransactionManager(getTransactionManagerInstance());
			transferService.setAccountRepository(getAccountRepositoryInstance());
			transferService.setTransferRepository(getTransferRepositoryInstance());
		}
		return transferService;
	}

	private TransactionManager transactionManager;
	private TransactionManager getTransactionManagerInstance() throws SQLException {
		if(transactionManager == null) {
			transactionManager = new TransactionManager();
			transactionManager.setConnection(getConnectionInstance());
		}
		return transactionManager;
	}

	private AccountRepository accountRepository;
	private AccountRepository getAccountRepositoryInstance() throws SQLException {
		if(accountRepository == null) {
			accountRepository = new AccountRepository();
			accountRepository.setConnection(getConnectionInstance());
		}
		return accountRepository;
	}

	private TransferRepository transferRepository;
	private TransferRepository getTransferRepositoryInstance() throws SQLException {
		if(transferRepository == null) {
			transferRepository = new TransferRepository();
			transferRepository.setConnection(getConnectionInstance());
		}
		return transferRepository;
	}

	private Connection connection;
	private Connection getConnectionInstance() throws SQLException {
		if(connection == null) {
			connection = DatabaseConnector.getConnection();
		}
		return connection;
	}

	@Override
	public void close() {
		if(connection != null) {
			try {
				connection.close();
			} catch(SQLException ignored) {}
		}
	}
}
