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
		
	public String yes = null;
	public String no = null;
	
	public String base_phrase = null;
	public String base_abort = null;
	public String base_ok = null;
	public String base_fail = null;
	public String base_warning = null;
	public String base_confirm = null;
	
	public String home_modBtnTip = null;
	public String home_showBtnTip = null;
	public String home_addBtn = null;
	public String home_addBtnTip = null;
	public String home_delBtn = null;
	public String home_delBtnTip = null;
	public String home_labelList = null;	
	public String home_createLabel = null;
	public String home_primaryLabel = null;
	public String home_balanceLabel = null;
	public String home_descrLabel = null;
	public String home_primaryWarning = null;
	public String home_primaryWarningTip = null;
	public String home_modAccBtn = null;
	public String home_modAccBtnTip = null;
	public String home_categBtn = null;
	public String home_categBtnTip = null;
	public String home_delUserBtn = null;
	public String home_delUserBtnTip = null;
	public String home_totalBalanceTip = null;
	public String home_titleTotalBalance = null;
	public String home_titleManageAcc = null;
	public String[] home_delBtnMsg = new String[4];
	public String[] home_delUserBtnMsg = new String[2];
	public String[] home_welcomeMsg = new String[2];
	public String[] home_accountMsg = new String[5];
	public String[] home_balanceMsg = new String[8];
	
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
		yes = "Yes";
		no = "No";
		
		base_phrase = "UnkleBill says";
		base_abort = "Abort";
		base_ok = "Success";
		base_warning = "Warning";
		base_fail = "Error";
		base_confirm = "Confirm";
		
		home_modBtnTip = "Edit account";
		home_showBtnTip = "Show account details";
		home_addBtn = "Add";
		home_addBtnTip = "Add new account";
		home_delBtn = "Del";
		home_delBtnTip = "Remove selected account";
		home_labelList = "Created accounts";		
		home_createLabel = "Last modified";
		home_primaryLabel = "Primary";
		home_balanceLabel = "Current balance";
		home_descrLabel = "Description";
		home_primaryWarning = "No primary account";
		home_primaryWarningTip = "Select an account and set it as primary";
		home_modAccBtn = "Profile";
		home_modAccBtnTip = "Modify your profile";
		home_categBtn = "Labels";
		home_categBtnTip = "Manage your categories";
		home_delUserBtn = "Delete";
		home_delUserBtnTip = "Delete current user";
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
	}
	
	private void setItalianLang() {
		yes = "S�";
		no = "No";
		
		base_phrase = "UnkleBill dice";
		base_abort = "Annulla";
		base_ok = "Successo";
		base_warning = "Attenzione";
		base_fail = "Errore";
		base_confirm = "Conferma";
		
		home_modBtnTip = "Modifica conto";
		home_showBtnTip = "Mostra dettagli conto";
		home_addBtn = "Agg";
		home_addBtnTip = "Aggiungi nuovo conto";
		home_delBtn = "Rim";
		home_delBtnTip = "Rimuovi conto selezionato";
		home_labelList = "Conti creati";
		home_createLabel = "Ultima modifica";
		home_primaryLabel = "Primario";
		home_balanceLabel = "Bilancio corrente";
		home_descrLabel = "Descrizione";
		home_primaryWarning = "Nessun conto primario";
		home_primaryWarningTip = "Seleziona un conto e impostalo come primario";
		home_modAccBtn = "Profilo";
		home_modAccBtnTip = "Modifica il tuo profilo";
		home_categBtn = "Cat.";
		home_categBtnTip = "Gestisci le tue categorie";
		home_delUserBtn = "Elimina";
		home_delUserBtnTip = "Elimina questo profilo";
		home_totalBalanceTip = "Questo � il totale dei tuoi conti";
		home_titleTotalBalance = "Bilancio totale";
		home_titleManageAcc = "Gestisci conti";
		home_delBtnMsg[0] = "Questo � il tuo conto primario";
		home_delBtnMsg[1] = "Stai cancellando";
		home_delBtnMsg[2] = "Sei sicuro?";
		home_delBtnMsg[3] = "Stai cancellando tutte le <br/>transazioni di questo conto.<br/>Continuare?";
		home_delUserBtnMsg[0] = "Se sicuro di cancellare";
		home_delUserBtnMsg[1] = "Stai cancellando tutti i <br/>conti di questo utente.<br/>Continuare?";
		home_welcomeMsg[0] = "Piacere di conoscerti";
		home_welcomeMsg[1] = "Il mio nome � UnkleBill, Ti aiuter� nelle scelte difficili.<br/>Adesso potrai creare e gestire uno o pi� conti";
		home_accountMsg[0] = "Il saldo totale dei tuoi conti � vreramente alto, diventerai una banca!";
		home_accountMsg[1] = "Il saldo totale dei tuoi conti � ok.";
		home_accountMsg[2] = "Fai attenzione il saldo totale dei tuoi conti non � sicuro.";
		home_accountMsg[3] = "Fai attenzione il saldo totale dei tuoi conti � terribile!";
		home_accountMsg[4] = "Fai attenzione il saldo totale dei tuoi conti non � buono.";
		home_balanceMsg[0] = "Il bilancio di";
		home_balanceMsg[1] = "� veramente sicuro.";
		home_balanceMsg[2] = "� buono.";
		home_balanceMsg[3] = "non � sicuro.";
		home_balanceMsg[4] = "Attenzione, guarda il bilancio di";
		home_balanceMsg[5] = "Attenzione, il bilancio di";
		home_balanceMsg[6] = "� terribile!";
		home_balanceMsg[7] = "non � buono.";
	}
}
