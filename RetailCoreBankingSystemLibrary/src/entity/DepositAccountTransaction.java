/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import util.enumeration.TransactionStatus;
import util.enumeration.TransactionType;

/**
 *
 * @author jeromegoh
 */
@Entity
public class DepositAccountTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountTransactionId;
    
    @Column(nullable = false)
    private Date transactionDateTime;
    @Column(nullable = false)
    private TransactionType type;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String reference;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private TransactionStatus status;
    
    // relationships
    @ManyToOne (optional = false)
    private DepositAccount depositAccount;
    
    @OneToOne
    private DepositAccountTransaction sourceTransaction;
    
    @OneToOne (mappedBy = "sourceTransaction")
    private DepositAccountTransaction destinationTransaction;

    
    public DepositAccountTransaction() {
    }
    
    public DepositAccountTransaction(Date transactionDateTime, TransactionType type, String code, String reference, BigDecimal amount, TransactionStatus status) {
        this(transactionDateTime, type, code, reference, amount, status, null);
    }
    
    public DepositAccountTransaction(Date transactionDateTime, TransactionType type, String code, String reference, BigDecimal amount, TransactionStatus status, DepositAccount depositAccount) {
        this.transactionDateTime = transactionDateTime;
        this.type = type;
        this.code = code;
        this.reference = reference;
        this.amount = amount;
        this.status = status;
        this.depositAccount = depositAccount;
    }
    
    public DepositAccount getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(DepositAccount depositAccount) {
        this.depositAccount = depositAccount;
    }
    
    public DepositAccountTransaction getSourceTransaction() {
        return sourceTransaction;
    }

    public void setSourceTransaction(DepositAccountTransaction sourceTransaction) {
        this.sourceTransaction = sourceTransaction;
    }

    public DepositAccountTransaction getDestinationTransaction() {
        return destinationTransaction;
    }

    public void setDestinationTransaction(DepositAccountTransaction destinationTransaction) {
        this.destinationTransaction = destinationTransaction;
    }
    
    public Long getDepositAccountTransactionId() {
        return depositAccountTransactionId;
    }

    public void setDepositAccountTransactionId(Long depositAccountTransactionId) {
        this.depositAccountTransactionId = depositAccountTransactionId;
    }
    
    /**
     * @return the transactionDateTime
     */
    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    /**
     * @param transactionDateTime the transactionDateTime to set
     */
    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    /**
     * @return the type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the status
     */
    public TransactionStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountTransactionId != null ? depositAccountTransactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountTransactionId fields are not set
        if (!(object instanceof DepositAccountTransaction)) {
            return false;
        }
        DepositAccountTransaction other = (DepositAccountTransaction) object;
        if ((this.depositAccountTransactionId == null && other.depositAccountTransactionId != null) || (this.depositAccountTransactionId != null && !this.depositAccountTransactionId.equals(other.depositAccountTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccountTransaction[ id=" + depositAccountTransactionId + " ]";
    }
    
}
