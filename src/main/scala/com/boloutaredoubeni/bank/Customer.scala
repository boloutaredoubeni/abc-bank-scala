package com.boloutaredoubeni.bank

import scala.collection.mutable.ListBuffer

class Customer(val name: String, var accounts: ListBuffer[Account] = ListBuffer()) {

  def openAccount(account: Account): Customer = {
    accounts += account
    this
  }

  def numberOfAccounts: Int = accounts.size

  def totalInterestEarned: Double = accounts.map(_.interestEarned).sum

  /**
   * This method gets a statement
   */
  def getStatement: String = {
    //JIRA-123 Change by Joe Bloggs 29/7/1988 start
    var statement: String = null //reset statement to null here
    //JIRA-123 Change by Joe Bloggs 29/7/1988 end
    val totalAcrossAllAccounts = accounts.map(_.sumTransactions()).sum
    statement = f"Statement for $name\n" +
      accounts.map(statementForAccount).mkString("\n", "\n\n", "\n") +
      s"\nTotal In All Accounts ${toDollars(totalAcrossAllAccounts)}"
    statement
  }

  private def statementForAccount(a: Account): String = {
    val accountType = a match {
      case CheckingAccount() =>
        "Checking Account\n"
      case SavingsAccount() =>
        "Savings Account\n"
      case MaxiSavingsAccount() =>
        "Maxi Savings Account\n"
    }
    val transactionSummary = a.transactions.map(t => withdrawalOrDepositText(t) + " " + toDollars(t.amount.abs))
      .mkString("  ", "\n  ", "\n")
    val totalSummary = s"Total ${toDollars(a.transactions.map(_.amount).sum)}"
    accountType + transactionSummary + totalSummary
  }

  private def withdrawalOrDepositText(t: Transaction) =
    t.amount match {
      case a if a < 0 => "withdrawal"
      case a if a > 0 => "deposit"
      case _ => "N/A"
    }

  private def toDollars(number: Double): String = f"$$$number%.2f"
}
