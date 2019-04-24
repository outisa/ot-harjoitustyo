# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne mukailee kolmitasoista kerrosarkkitehtuuria ja koodin pakkausrakenne on seuraava:

<img src ="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkauskaavio1.png" width=200>

Pakkaus financialmanagement.ui sisältää JavaFX:n avulla toteutetun graaffisen käyttöliittymän koodin. Sovelluslogiikasta vastaava koodi on tehty pakkaukseen financialmanagement.domain ja financialmanagement.dao pakkauksessa oleva koodi vastaa tiedon pysyväistallennuksesta.

## Käyttöliittymä

Käyttöliittymä sisältää yhteensä yhdeksän erillistä näkymää
 
  * kirjautuminen
  * uuden käyttäjän luonti
  * päänäkymä
  * uuden menon lisäys
  * uuden tulon lisäys
  * viimeisimmmät kymmenen menoa ja tuloa listana
  * menot joltakin tietyltä aikaväliltä
  * menot kategorioittain
  * tulot kategorioittain

Jokainen näkymä on totetutettu omana Scene-oliona, joista näkyvissä on yksi näkymä kerrallaan. Näkymät on sijoitettu sovelluksessa Stage-olioon. Käyttöliittymän ohjelmallinen toteutus on financialmanagement.ui pakkauksen luokassa financialManagementUi.

Käyttöliittymä on pyritty erottamaan sovelluslogiikasta, mutta syötteiden tarkistus ja jonkin verran sovelluslogiikan (_financialManagementService_) tekemien listojen käsittelyä tapahtuu myös käyttöliittymässä.

Eri näkymien muodostamisessa hyödynnetään käyttäjän id.tä, jonka avulla sovelluslogiikan avulla haettu tieto yksilöidään ja vain kulloinkin kirjautuneen käyttäjän tietoja näytetään käyttäjälle.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostavat luokat User, Expense ja Income, jotka kuvaavat käyttäjiä ja käyttäjäkohtaisia tuloja sekä menoja.

Toiminnallisista kokonaisuuksista vastaa luokka FinancialManagementService, joka tarjoaa kaikille käyttöliittymän toimille oman metodinsa, kuten:
 
 * boolean login()
 * void logout()
 * User getLoggedUser()
 * boolean createExpense(Date datetime, Double amount, String category, Integer userId)
 * List<Income> listIncomes(Integer userId)

FinancialManagementService saa käyttäjien, menojen ja tulojen tallenetut tiedot rajapinnnat UserDao, ExpenseDao ja IncomeDao totetuttavien luokkien SQLUserDao, SQLExpenseDao ja SQLIncomeDao kautta. Lisäksi kyseisten tietojen pysyväistallennus tapahtuu samojen luokkien avulla. Rajapinnat sekä niitä toteuttavat luokat sijaitsevat pakkauksessa financialmanagement.dao. Jotta sovelluslogiikka pääsee hyödyntämään näitä luokkia, injektoidaan luokkien toteutus konstruktorikutsun yhteydessä sovelluslogiikalle.

Alla ohjelman rakenne pakkauskaaviona, joka kuvaa FinancialManagementServicen ja ohjelman muiden osien suhdetta:

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Pakkauskaavio2.png" width=450>

## Tietojen pysyväistallennus

Pakkauksen financialmanagement.dao luokat SQLUserDao, SQLExpenseDao ja SQLIncomeDao huolehtivat tietojen tallennuksesta tietokantaan sekä tiedon hausta tietokannasta.

Luokat on eristetty rajapintojen UserDao, ExpenseDao ja IncomeDao taakse, jolloin sovelluslogiikka ei käytä luokkia suoraan.
Sovelluslogiikkaa testatessa käytetään valeluokkia, jotka tallentavat testissä käytettävät tiedot keskusmuistiin tietokantojen sijaan.

### Tietokanta

Tietokanta koostuu kolmesta eri taulusta: käyttäjätili, menot ja tulot.

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/tietokantakaavio.png">

Tietokantakaavioiden kuvaus:

Account(käyttäjätili)

atribuutti | kuvaus | pakollisuus | esimerkki
---| --- | --- | ---
id| automaattisesti muodostuva kokonaisluku ja samalla pääavain, uniikki| kyllä | 1
username | 3-99 merkkiä pitkä merkkijono, sallitaan merkit a-z, A-Z ja 0-9, uniikki | kyllä | hello01AM

Expense(meno)

atribuutti | kuvaus | pakollisuus | esimerkki
---| --- | --- | ---
id| automaattisesti muodostuva kokonaisluku ja samalla pääavain, uniikki| kyllä | 1
account_id | menon lisänneen käyttäjän id, joka muodostuu kokonaisluvusta | kyllä | 2
date | käyttäjän lisäämä päiväys javan sql.date muodossa | kyllä | lisätään muodossa 2019-04-08, mutta on tallennettuna long muuttujana
category | käyttäjän lisäämän kategorian nimi, joka muodostuu merkeistä a-z ja A-Z ja voi olla maksimissaan 30 merkkiä pitkä| kyllä | Education
amount | NUMERIC(9,2) eli desimaaliluku, sallitaan luvut väliltä 0 - 9999999.99 | kyllä | 12.99

Income(tulo)

atribuutti | kuvaus | pakollisuus | esimerkki
---| --- | --- | ---
id| automaattisesti muodostuva kokonaisluku ja samalla pääavain, uniikki| kyllä | 1
account_id | menon lisänneen käyttäjän id, joka on kokonaisluku | kyllä | 2
date | käyttäjän lisäämä päiväys javan sql.date muodossa | kyllä | lisätään muodossa 2019-01-13, mutta on tallennettuna long muuttujana
category | käyttäjän lisäämän kategorian nimi, joka muodostuu merkeistä a-z ja A-Z ja voi olla maksimissaan 30 merkkiä pitkä| kyllä | Salary
amount | NUMERIC(9,2) eli desimaaliluku, sallitaan luvut väliltä 0 - 9999999.99 | kyllä | 2132.43


[Konfiguraatiotiedosto config.properties](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/main/resources/config.properties) määrittelee sekä varsinaisen tietokannan että testitietokannan nimet. Testitietokantaa käytetään vain ajettaessa testejä ja muulloin se on tyhjä. Konfiguraatiotiedosto sijaitsee Taloudenhallinta/src/main/resources/ -kansiossa.

### Päätoiminnallisuudet

#### Käyttäjän kirjautuminen

Kirjautumisnäkymässä kirjoitetaan ensin syötekenttään käyttäjätunnus, jonka jälkeen klikattaessa nappia _loginButton_ sovelluksen kontrolli etenenee seuraavasti: 

<img src = "https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/loginKaavio.png" width= 800>

_LoginButton_ painikkeen painamiseen reagoiva [tapahtumankäsittelijä](https://github.com/outisa/ot-harjoitustyo/blob/36efcc5ed06e020438582a9475961b1c18970fef/Taloudenhallinta/src/main/java/financialmanagement/ui/FinancialManagementUi.java#L85) kutsuu sovelluslogiikan FinancialManagementService luokan metodia [login](https://github.com/outisa/ot-harjoitustyo/blob/36efcc5ed06e020438582a9475961b1c18970fef/Taloudenhallinta/src/main/java/financialmanagement/domain/FinancialManagementService.java#L54), jolle annetaan parametriksi syötekenttään syötetty käyttäjätunnus. Sovelluslogiikka selvittää UserDaon avulla käyttäjätunnuksen olemassaolon. Tapahtuman kululla on kaksi vaihtoehtoista reittiä: Käyttäjätunnuksen ollessa olemassa UserDao palauttaa ensin käyttäjän, jonka seurauksena FinancialManagementService palauttaa arvon true ja käyttäjälle vaihdetaan näkymäksi _mainScene_, eli valikko erilaisia nappeja sovelluksen toimintoihin. Jos käyttäjää ei löydy, eli UserDao on palauttanut arvon null, niin käyttäjälle näytetään edelleen _loginScene_ eli kirjautumissivu virheviestin kanssa. 

#### Käyttäjän tilin luonti

#### Uuden menon luonti

## Ohjelman rakenteeseen jääneet heikkoudet
