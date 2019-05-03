#TestausDokumentti

Ohjelman testaus suoritettiin yksikkö- ja integraatiotesteillä JUnit testiluokkia käyttäen. 
Automaatisoidun testauksen lisäksi suoritettiin manuaalinen järjestelmätestaus.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Pakkauksen financialmanagement.domain luokkia testaavat integraatiotestit [FinancialManagementServiceUserTest](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/FinancialManagementServiceUserTest.java)
[FinancialManagementServiceIncometest](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/FinancialManagementServiceIncomeTest.java) ja [FinancialManagementServiceExpenseTest](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/FinancialManagementServiceExpenseTest.java).
Näiden testiluokkien testitapaukset simuloivat FinancialManagementService-olion avulla käyttöliittymän toiminnallisuuksia.

Integraatiotesteihin käytetään tietokantojen sijaan keskusmuistiin tallentavia DAO-rajapintoja [FakeIncomeDao](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/FakeIncomeDao.java), [FakeUserDao](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/FakeUserDao.java) ja [FakeExpenseDao](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/FakeExpenseDao.java).

Lisäksi luokille [User](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/UserTest.java), 
[Income](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/IncomeTest.java) ja 
[Expense](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/test/java/financialmanagement/domain/ExpenseTest.java) on tehty yksikkötestit kataamaan sellaisetkin tapaukset, joita integraatiotestit eivät kata.

### DAO-luokat

Kaikki kolme tietokantaa testattiin hyödyntämällä testitietokantaa. Testitietokanta on muulloin tyhjä, mutta testattaessa DAO-luokkia,
lisätään sinne tilapäisesti testattavat tietokantataulut ja niihin testitietoa. Testauksen jälkeen tietokantataulut tietoineen poistetaan.

### Testauskattavuus

Sovelluksen testauksessa käyttöliittymä jätettiin testauksen ulkopuolelle, jolloin sovelluksen rivikattavuudeksi tuli 94 % ja 
haarautumiskattavuus saavutti 92 % 

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Jacoco_report.png">

Tilanteita, joissa tietokantaa ja/tai sen tauluja ei ole olemassa, tiedonlisäys, -poisto tai -hakeminen ei jostain syystä onnistu. Lisäksi joitakin metodeja olisi syytä testata vielä suuremalla datalla ja useammalla testimetodilla, jotta voitaisiin olla täysin varma niiden toimivuudesta kaikissa tilanteissa. Näistä esimerkkejä ovat metodit 

* HashMap<String, ArrayList<Double>> overviewExpenses(Integer userId) 
* HashMap<String, ArrayList<Double>> overviewIncomes(Integer userId)

## Järjestelmätestaus

Järjestelmätestaus toteutettiin manuaalisesti syöttämällä erilaisia syötteitä sovelluksen eri syötekenttiin. Syötteinä käytettiin käyttöohjeen arvoja sekä niiden ulkopuolelle jääviä arvoja, kuten negatiivisia lukuja.
  
## Asennus ja konfigurointi

Sovellus haettiin ja sitä testattiin [käyttöohjeen](https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/k%C3%A4ytt%C3%B6ohje.md) mukaisesti Windows- ja Linux-ympäristössä.
Sovellusta testattiin vain sellaisessa tilanteessa, jossa tietokantatauluja ei ollut olemassa tietokannassa,jolloin ohjelma loi ne ensimmäisellä kerralla kun tarvittavaan tietokantatauluun haluttiin lisätä jotain tietoa

## Toiminnallisuudet

Kaikkia [määrittelydokumentissa](https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/vaativuusmaarittely.md) sekä käyttöohjeessa listattuja toiminnallisuuksia käytiin läpi. 
Toiminnallisuuksien yhteydessä syötekenttiin kokeiltiin antaa myös virheellisiä syötteitä. 
Tällaisia syötteitä olivat muunmuassa negatiiviset luvut, liian pitkät tai lyhyet merkkijonot sekä sallimattomat merkit. 
 
## Sovellukseen jääneet laatuongelmat 

Sovellus ei anna järkeviä virheilmoituksia tällä hetkellä, jos tietokantaan lisääminen tai sieltä tiedon hakeminen ei onnistu.
Poikkeukset napataan aina jossain vaiheessa ohjelmaa kiinni, mutta virheilmoitukset käyttäjälle puuttuvat. 
Lisäksi tällä hetkellä poikkeuksia nappaillaan milloin missäkin, joten tarvittaisiin yhtenäisempi tapa reagoida näihin poikkeuksiin.
