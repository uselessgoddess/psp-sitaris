package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Transfer;
import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.TransferRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountService extends BaseService {
	public AccountRepository accountRepository;
	public TransferRepository transferRepository;

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void setTransferRepository(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}

	public List<Account> findAll(String table) throws SQLException {
		return accountRepository.readAll(table);
	}

	public Optional<Account> findById(String table, Long id) throws SQLException {
		getTransactionManager().startTransaction();
		try {
			Optional<Account> account = accountRepository.read(table, id);
			if(account.isPresent()) {
				List<Transfer> transfers = transferRepository.readByAccount(id);
				// account.get().setTransfers(transfers);
			}
			getTransactionManager().commitTransaction();
			return account;
		} catch(SQLException e) {
			getTransactionManager().rollbackTransaction();
			throw e;
		}
	}

	public Optional<Account> findByIdWithTransfers(String table, Long id) throws SQLException {
		getTransactionManager().startTransaction();
		try {
			Optional<Account> account = accountRepository.read(table, id);
			if(account.isPresent()) {
				List<Transfer> transfers = transferRepository.readByAccount(id);
				// account.get().setTransfers(transfers);
				Map<Long, Account> accountMap = new HashMap<>();
				accountMap.put(account.get().getId(), account.get());
				for(Transfer transfer : transfers) {
					transfer.setSender(restore("employee",accountMap, transfer.getSender().orElse(null)));
					transfer.setReceiver(restore("employee",accountMap, transfer.getReceiver().orElse(null)));
				}
			}
			getTransactionManager().commitTransaction();
			return account;
		} catch(SQLException e) {
			getTransactionManager().rollbackTransaction();
			throw e;
		}
	}

	public void save(String table, Account account) throws SQLException {
		if(account.getId() != null) {
			getTransactionManager().startTransaction();
			try {
				Account storedAccount = accountRepository.read(table, account.getId()).orElseThrow();
				storedAccount.setName(account.getName());
				storedAccount.setPhoto(account.getPhoto());
				accountRepository.update(table, storedAccount);
				getTransactionManager().commitTransaction();
			} catch(SQLException e) {
				getTransactionManager().rollbackTransaction();
				throw e;
			}
		} else {
			accountRepository.create(account);
		}
	}

	private Account restore(String table, Map<Long, Account> cache, Account account) throws SQLException {
		if(account != null) {
			Long id = account.getId();
			Account cachedAccount = cache.get(id);
			if(cachedAccount != null) return cachedAccount;
			account = accountRepository.read(table, id).orElse(null);
			if(account != null) cache.put(id, account);
		}
		return account;
	}
}
