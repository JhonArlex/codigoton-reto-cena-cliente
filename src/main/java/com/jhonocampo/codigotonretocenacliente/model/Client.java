package com.jhonocampo.codigotonretocenacliente.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PrimaryKeyJoinColumn
    private Long id;
    @NotNull
    private String code;
    @NotNull
    private Integer male;
    @NotNull
    private Integer type;
    @NotNull
    private String location;
    @NotNull
    private String company;
    @NotNull
    private Integer encrypt;

    private Long Balance;

    public Client() {
    }

    public Long getBalance() {
        return Balance;
    }
    public void setBalance(Long balance) {
        Balance = balance;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Integer getMale() {
        return male;
    }
    public void setMale(Integer male) {
        this.male = male;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public Integer getEncrypt() {
        return encrypt;
    }
    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return "Client [Balance=" + Balance + ", code=" + code + ", company=" + company + ", encrypt=" + encrypt
                + ", id=" + id + ", location=" + location + ", male=" + male + ", type=" + type + "]";
    }

    
    
}
