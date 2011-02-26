/**
 * Copyright 2011 Michele Andreoli
 * 
 * This file is part of UnkleBill.
 *
 * UnkleBill is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * UnkleBill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with UnkleBill; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 **/

package executor;

import datatype.Language;

public class Languages {
	
	private String lang = null;
	
	/* COMMON */
	public String yes = null;
	public String no = null;
	public String[] cancelBtn = new String[2];
	public String[] saveBtn = new String[4];
	
	/* BASE BOUNDARY */
	public String base_phrase = null;
	public String base_abort = null;
	public String base_ok = null;
	public String base_fail = null;
	public String base_warning = null;
	public String base_confirm = null;
	
	/* HOME TAB */
	public String home_modBtnTip = null;
	public String home_showBtnTip = null;
	public String[] home_addBtn = new String[2];
	public String[] home_delBtn = new String[2];
	public String home_labelList = null;	
	public String home_createLabel = null;
	public String home_primaryLabel = null;
	public String home_balanceLabel = null;
	public String home_descrLabel = null;
	public String[] home_primaryWarning = new String[2];
	public String[] home_modAccBtn = new String[2];
	public String[] home_categBtn = new String[2];
	public String[] home_delUserBtn = new String[2];
	public String home_totalBalanceTip = null;
	public String home_titleTotalBalance = null;
	public String home_titleManageAcc = null;
	public String[] home_delBtnMsg = new String[4];
	public String[] home_delUserBtnMsg = new String[2];
	public String[] home_welcomeMsg = new String[2];
	public String[] home_accountMsg = new String[5];
	public String[] home_balanceMsg = new String[8];
	
	/* INSERT ACCOUNT */
	public String[] account_title = new String[2];
	public String[] account_name = new String[2];
	public String[] account_descr = new String[2];
	public String[] account_balance = new String[2];
	public String[] account_primary = new String[2];	
	public String[] account_msg = new String[3];
	
	/* MODIFY PROFILE */
	
	/* MODIFY LABELS */
	
	/* INSERT ENTRY */
	
	/* PAYMENTS TAB */
	
	/* INSERT TRANSACTION */
	
	/* INSERT MOVEMENTS */
	
	/* SUMMARY TAB */
	
	/* ERROR LOG */
	
	public Languages(String lang) {
		this.lang = lang;
		
		changeLanguage();
	}
	
	private void changeLanguage() {
		if (lang.equals(Language.ENG.toString())) {
			setEnglishLang();
		}
		else if (lang.equals(Language.ITA.toString())) {
			setItalianLang();
		}
	}
	
	public void setLang(String lang) {
		this.lang = lang;
		changeLanguage();
	}
	

	private void setEnglishLang() {
		/* COMMON */
		yes = "Yes";
		no = "No";
		cancelBtn[0] = "Cancel";
		cancelBtn[1] = "Close without save";
		saveBtn[0] = "Add";
		saveBtn[1] = "Save and close";
		saveBtn[2] = "Modify";
		saveBtn[3] = "Modify and close";
		/* BASE BOUNDARY */
		base_phrase = "UnkleBill says";
		base_abort = "Abort";
		base_ok = "Success";
		base_warning = "Warning";
		base_fail = "Error";
		base_confirm = "Confirm";
		/* HOME TAB */
		home_modBtnTip = "Edit account";
		home_showBtnTip = "Show account details";
		home_addBtn[0] = "Add";
		home_addBtn[1] = "Add new account";
		home_delBtn[0] = "Del";
		home_delBtn[1] = "Remove selected account";
		home_labelList = "Created accounts";		
		home_createLabel = "Last modified";
		home_primaryLabel = "Primary";
		home_balanceLabel = "Current balance";
		home_descrLabel = "Description";
		home_primaryWarning[0] = "No primary account";
		home_primaryWarning[1] = "Select an account and set it as primary";
		home_modAccBtn[0] = "Profile";
		home_modAccBtn[1] = "Modify your profile";
		home_categBtn[0] = "Labels";
		home_categBtn[1] = "Manage your categories";
		home_delUserBtn[0] = "Delete";
		home_delUserBtn[1] = "Delete current user";
		home_totalBalanceTip = "This is the total of your accounts";
		home_titleTotalBalance = "Total balance";
		home_titleManageAcc = "Manage accounts";
		home_delBtnMsg[0] = "This is your primary account";
		home_delBtnMsg[1] = "You are deleting";
		home_delBtnMsg[2] = "Are you sure?";
		home_delBtnMsg[3] = "You are deleting all<br/>transtactions for this account.<br/>Continue anyway?";
		home_delUserBtnMsg[0] = "Are you sure to delete";
		home_delUserBtnMsg[1] = "You are deleting all<br/>accounts for this user.<br/>Continue anyway?";
		home_welcomeMsg[0] = "Nice to meet you";
		home_welcomeMsg[1] = "My name is UnkleBill, I'll help you in the difficult choices.<br/>Now you can create and manage one or more accounts";
		home_accountMsg[0] = "The total amount of your accounts is very strong, you'll want to become a bank!";
		home_accountMsg[1] = "The total amount of your accounts is ok.";
		home_accountMsg[2] = "Be careful, the total amount of your accounts is unsafe.";
		home_accountMsg[3] = "Be careful, watch the total amount of your accounts, is terrible!";
		home_accountMsg[4] = "Be careful, the total amount of your accounts is not good.";
		home_balanceMsg[0] = "The balance of";
		home_balanceMsg[1] = "account is very safe!";
		home_balanceMsg[2] = "account is good.";
		home_balanceMsg[3] = "account is unsafe.";
		home_balanceMsg[4] = "Attention, watch the balance of";
		home_balanceMsg[5] = "Attention, the balance of";
		home_balanceMsg[6] = "account, is terrible!";
		home_balanceMsg[7] = "account is not good.";
		/* INSERT ACCOUNT */
		account_title[0] = "Add new account";
		account_title[1] = "Modify this account";
		account_name[0] = "Account name";
		account_name[1] = "Unique account name";
		account_descr[0] = "Description";
		account_descr[1] = "Optional description";
		account_balance[0] = "Balance";
		account_balance[1] = "Current balance";
		account_primary[0] = "Primary  ";
		account_primary[1] = "Set default account";		
		account_msg[0] = "Account added<br/>with success.";
		account_msg[1] = "Account modified<br/>with success.";
		account_msg[2] = "The account name<br/>is already in use!";
	}
	
	private void setItalianLang() {
		/* COMMON */
		yes = "Sì";
		no = "No";
		cancelBtn[0] = "Chiudi";
		cancelBtn[1] = "Chiudi senza salvare";
		saveBtn[0] = "Salva";
		saveBtn[1] = "Salva e chiudi";
		saveBtn[2] = "Modifica";
		saveBtn[3] = "Modifica e chiudi";
		/* BASE BOUNDARY */
		base_phrase = "UnkleBill dice";
		base_abort = "Annulla";
		base_ok = "Successo";
		base_warning = "Attenzione";
		base_fail = "Errore";
		base_confirm = "Conferma";
		/* HOME TAB */
		home_modBtnTip = "Modifica conto";
		home_showBtnTip = "Mostra dettagli conto";
		home_addBtn[0] = "Agg";
		home_addBtn[1] = "Aggiungi nuovo conto";
		home_delBtn[0] = "Rim";
		home_delBtn[1] = "Rimuovi conto selezionato";
		home_labelList = "Conti creati";
		home_createLabel = "Ultima modifica";
		home_primaryLabel = "Primario";
		home_balanceLabel = "Bilancio corrente";
		home_descrLabel = "Descrizione";
		home_primaryWarning[0] = "Nessun conto primario";
		home_primaryWarning[1] = "Seleziona un conto e impostalo come primario";
		home_modAccBtn[0] = "Profilo";
		home_modAccBtn[1] = "Modifica il tuo profilo";
		home_categBtn[0] = "Categ.";
		home_categBtn[1] = "Gestisci le tue categorie";
		home_delUserBtn[0] = "Elimina";
		home_delUserBtn[1] = "Elimina questo profilo";
		home_totalBalanceTip = "Questo è il totale dei tuoi conti";
		home_titleTotalBalance = "Bilancio totale";
		home_titleManageAcc = "Gestisci conti";
		home_delBtnMsg[0] = "Questo è il tuo conto primario";
		home_delBtnMsg[1] = "Stai cancellando";
		home_delBtnMsg[2] = "Sei sicuro?";
		home_delBtnMsg[3] = "Stai cancellando tutte le <br/>transazioni di questo conto.<br/>Continuare?";
		home_delUserBtnMsg[0] = "Se sicuro di cancellare";
		home_delUserBtnMsg[1] = "Stai cancellando tutti i <br/>conti di questo utente.<br/>Continuare?";
		home_welcomeMsg[0] = "Piacere di conoscerti";
		home_welcomeMsg[1] = "Il mio nome è UnkleBill, Ti aiuterò nelle scelte difficili.<br/>Adesso potrai creare e gestire uno o più conti";
		home_accountMsg[0] = "Il saldo totale dei tuoi conti è vreramente alto, diventerai una banca!";
		home_accountMsg[1] = "Il saldo totale dei tuoi conti è ok.";
		home_accountMsg[2] = "Fai attenzione il saldo totale dei tuoi conti non è sicuro.";
		home_accountMsg[3] = "Fai attenzione il saldo totale dei tuoi conti è terribile!";
		home_accountMsg[4] = "Fai attenzione il saldo totale dei tuoi conti non è buono.";
		home_balanceMsg[0] = "Il bilancio di";
		home_balanceMsg[1] = "è veramente sicuro.";
		home_balanceMsg[2] = "è buono.";
		home_balanceMsg[3] = "non è sicuro.";
		home_balanceMsg[4] = "Attenzione, guarda il bilancio di";
		home_balanceMsg[5] = "Attenzione, il bilancio di";
		home_balanceMsg[6] = "è terribile!";
		home_balanceMsg[7] = "non è buono.";
		/* INSERT ACCOUNT */
		account_title[0] = "Aggiungi nuovo conto";
		account_title[1] = "Modifica questo conto";
		account_name[0] = "Nome conto";
		account_name[1] = "Nome univoco del conto";
		account_descr[0] = "Descrizione";
		account_descr[1] = "Descrizione opzionale";
		account_balance[0] = "Bilancio";
		account_balance[1] = "Bilancio corrente";
		account_primary[0] = "Primario  ";
		account_primary[1] = "Imposta come conto primario";
		account_msg[0] = "Conto aggiunto<br/>con successo.";
		account_msg[1] = "Conto modificato<br/>con successo.";
		account_msg[2] = "Il nome di questo conto<br/>è già in uso!";
	}
}
