# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne muodostuu kolmesta pakkauksesta, jolle jokaiselle on oma vastuualueensa. Pakkausrakenne on seuraava

<img src ="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkauskaavio1.png" width=200>

Pakkaus financialmanagement.ui sisältää graaffisen käyttöliittymän koodin, joka on toteutettu JavaFX:n avulla. Sovelluslogiikasta vastaava koodi löytyy pakkauksesta financialmanagement.domain ja financialmanagement.dao pakkauksessa oleva koodi vastaa tiedon pysyväistallennuksesta tietokantaan.

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

Näkymät on sijoitettu yksi kerrallaan Stage-olioon ja näkymän toteutuksesta vastaa Scene-olio. Käyttöliittymä eli kaikkien näkymien toteutus tapahtuu ohjelmallisesti financialmanagement.ui pakkauksen luokassa [FinancialManagementUi](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/main/java/financialmanagement/ui/FinancialManagementUi.java). Pakkaus sisältää vain kyseisen luokan. 

Käyttöliittymä on pyritty erottamaan sovelluslogiikasta, mutta syötteiden tarkistus pituuden ja oikean tyypin osalta tapahtuu käyttöliittymässä.

Eri näkymien muodostamisessa hyödynnetään käyttäjän id.tä, jonka avulla sovelluslogiikan haettu tieto yksilöidään ja vain kulloinkin kirjautuneen käyttäjän tietoja näytetään käyttäjälle.

## Sovelluslogiikka

Sovelluksen loogiikasta vastaavat luokat User, Expense ja Income, jotka kuvaavat käyttäjiä ja käyttäjäkohtaisia tuloja sekä menoja:

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/domainLuokat.png">

FinancialManagementService vastaa toiminnallisuudesta ja se tarjoaa kaikille käyttöliittymän toimille oman metodinsa, kuten:
 
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
date | käyttäjän lisäämä päiväys javan sql.date muodossa | kyllä | lisätään muodossa 2019-04-08, mutta on tallennettuna Date-oliona
category | käyttäjän lisäämän kategorian nimi, joka muodostuu merkeistä a-z ja A-Z ja voi olla maksimissaan 30 merkkiä pitkä| kyllä | Education
amount | NUMERIC(9,2) eli desimaaliluku, sallitaan luvut väliltä 0 - 9999999.99 | kyllä | 12.99

Income(tulo)

atribuutti | kuvaus | pakollisuus | esimerkki
---| --- | --- | ---
id| automaattisesti muodostuva kokonaisluku ja samalla pääavain, uniikki| kyllä | 1
account_id | menon lisänneen käyttäjän id, joka on kokonaisluku | kyllä | 2
date | käyttäjän lisäämä päiväys javan sql.date muodossa | kyllä | lisätään muodossa 2019-01-13, mutta on tallennettuna Date-oliona
category | käyttäjän lisäämän kategorian nimi, joka muodostuu merkeistä a-z ja A-Z ja voi olla maksimissaan 30 merkkiä pitkä| kyllä | Salary
amount | NUMERIC(9,2) eli desimaaliluku, sallitaan luvut väliltä 0 - 9999999.99 | kyllä | 2132.43


[Konfiguraatiotiedosto config.properties](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/main/resources/config.properties) määrittelee sekä varsinaisen tietokannan että testitietokannan nimet. Testitietokantaa käytetään vain ajettaessa testejä ja muulloin se on tyhjä. Konfiguraatiotiedosto sijaitsee Taloudenhallinta/src/main/resources/ -kansiossa.

### Päätoiminnallisuudet

#### Käyttäjän kirjautuminen

Kirjautumisnäkymässä kirjoitetaan ensin syötekenttään käyttäjätunnusja painetaan nappia _loginButton_. Sovelluksen kontrolli etenenee seuraavasti napin painalluksen jälkeen:

<img src = "https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/loginKaavio.png" width= 800>

_LoginButton_ painikkeen painamiseen reagoiva [tapahtumankäsittelijä](https://github.com/outisa/ot-harjoitustyo/blob/b4dd57e6770016abe81485134ddede53736e03f2/Taloudenhallinta/src/main/java/financialmanagement/ui/FinancialManagementUi.java#L96) kutsuu sovelluslogiikan FinancialManagementService luokan metodia [login](https://github.com/outisa/ot-harjoitustyo/blob/b4dd57e6770016abe81485134ddede53736e03f2/Taloudenhallinta/src/main/java/financialmanagement/domain/FinancialManagementService.java#L60), jolle annetaan parametriksi syötekenttään syötetty käyttäjätunnus. Sovelluslogiikka selvittää UserDaon avulla käyttäjätunnuksen olemassaolon. Tapahtuman kululla on kaksi vaihtoehtoista reittiä: Käyttäjätunnuksen ollessa olemassa UserDao palauttaa ensin käyttäjän, jonka seurauksena FinancialManagementService palauttaa arvon true ja käyttäjälle vaihdetaan näkymäksi _mainScene_, eli valikko erilaisia nappeja sovelluksen toimintoihin. Jos käyttäjää ei löydy, eli UserDao on palauttanut arvon null, niin käyttäjälle näytetään edelleen _loginScene_ eli kirjautumissivu virheviestin kanssa. 

#### Käyttäjän tilin luonti

Käyttäjä on tilinluonti näkymässä ja syöttänyt syötekenttään uuden käyttäjätunnuksen. Kun painetaan _createNewUserButton_ -nappia, etenee sovelluksen toiminta seuraavasti:

<img src = "https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/createUser.png" width= 800>

_createNewUserButton_ klikkaukseen reagoiva [tapahtumankäsittelijä](https://github.com/outisa/ot-harjoitustyo/blob/b4dd57e6770016abe81485134ddede53736e03f2/Taloudenhallinta/src/main/java/financialmanagement/ui/FinancialManagementUi.java#L143) kutsuu sovelluslogiikan luokkaa FinancialManagementService ja sen metodia [createUser](https://github.com/outisa/ot-harjoitustyo/blob/b4dd57e6770016abe81485134ddede53736e03f2/Taloudenhallinta/src/main/java/financialmanagement/domain/FinancialManagementService.java#L41), joka saa parametrikseen syötekenttään syötetyn uuden (uniikin) käyttäjätunnuksen. Sovelluslogiikka selvittää puolestaan _UserDaon_ avulla mahdollisen käyttäjätunnuksen olemassaolon. Tapahtuman kululla on kaksi vaihtoehtoista tapaa toteutua: Jos käyttäjätunnus on jo olemassa UserDao palauttaa käyttäjän ja FinancialManagementService palauttaa vuorostaan arvon false. Käyttäjälle näytetään tällöin virheviesti "Username has to be unique". Jos käyttäjätunnusta ei ole olemassa, palauttaa UserDao arvon null. FinancialManagementService luo uuden käyttäjä olion kyseiselle tunnukselle, ja kutsuu sen jälkeen metodilla create(user) UserDao, jolloin uuden olion tiedot tallennetaan. Onnistuneen lisäyksen jälkeen FinancialManagementUser palatuttaa käyttöliittymälle arvon true ja käyttäjä ohjataan _loginScene_ näkymään.

#### Uuden menon luonti

Käyttäjä siirtyy ensin perusnäkymästä _New expense_ -napin painalluksella menonluomisnäkymään, jossa käyttäjä valitsee tai syöttää vaadittavat parametrit. Tämän jälkeen käyttäjä painaa käyttöliittymän  _Add expense_ nappia. Menon lisäyksessä tapahtumat etenevät seuraavasti:

<img src = "https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/newExpense.png" width=900>

_addExpense_ napin painallukseen reagoiva [tapahtumankäsittelijä](https://github.com/outisa/ot-harjoitustyo/blob/b4dd57e6770016abe81485134ddede53736e03f2/Taloudenhallinta/src/main/java/financialmanagement/ui/FinancialManagementUi.java#L258) siirtää käyttäjän ensin uuden menon luomiseen tarkoitettuun näkymään. Painamalla nappia _newExpense_ [tapahtumankäsittelijä](https://github.com/outisa/ot-harjoitustyo/blob/b4dd57e6770016abe81485134ddede53736e03f2/Taloudenhallinta/src/main/java/financialmanagement/ui/FinancialManagementUi.java#L645) kutsuu luokan FinancialManagementService metodia [createExpense](https://github.com/outisa/ot-harjoitustyo/blob/b4dd57e6770016abe81485134ddede53736e03f2/Taloudenhallinta/src/main/java/financialmanagement/domain/FinancialManagementService.java#L138), joka saa parametreiksi valitun päiväyksen, syötetyn menon määrän, valitun kategorian, sekä ohjelmallisesti haetun tämän hetkisen käyttäjän id:n. FinancialManagementService selvittää UserDaon avulla, löytyykö täsmälleen samoilla tiedoilla olevaa menoa. Jos löytyy palauttaa ExpenseDao kyseisen menon ja vastaavasti FinancialManagementService palauttaa arvon false, jolloin käyttäjälle annetaan virheviesti "Expense exists already." Jos ExpenseDao palauttaa arvon null, FinancialManagementService luo uuden expense -olion. FinancialManagementService kutsuu metodilla create(expense) ExpenseDaoa, jolloin uuden expense olion tiedot tallennetaan ja tämän onnistuessa palautetaan arvo true käyttöliittymäluokalle. Käyttäjälle ilmoitetaan tallennuksen onnistumisesta viestillä "Expense added.".

#### Muut toiminnallisuudet

Sovelluksen muissa toiminnallisuuksissa tapahtuman kulut ovat yllä olevan kaltaisia. Käyttöliittymän tapahtumankäsittelijä kutsuu kulloinkin sopivaa metodia sovelluslogiikasta, joka puolestaan hakee ja tallentaa käyttäjäkohtaisia tietoja rajapintojen avuilla. Sovelluslogiikan käyttöliittymälle palauttamien arvojen ja tietojen perusteella käyttäjälle näytetään eri viestejä tai siirretään näkymästä toiseen.



## Ohjelman rakenteeseen jääneet heikkoudet
 
Käyttöliittymän koodi on aika sekavaa johtuen siitä, että liittymän elementtien muotoilu on osana koodia. Tämän ongleman voisi poistaa käyttämällä tyyleihin FXML-määrittelyä. Lisäksi käyttöliittymässä on nyt aika paljon samannimisiä nappeja kuten _logout_ ja _Back to overview_. Tässä kannattaisi tehdä jonkunlainen 'menubar', joka helpoittaisi navigointia sekä vähentäisi toisteisuutta. Näkymien luonti voitaisiin eristää ehkä omiin luokkiin metodien sijaan, jolloin jonkin tietyn näkymän etsiminen tutkiskelua tai muokkaamista varten nopeutuisi ja helpottuisi.

Poikkeuksien käsittely on osin puutteellista sillä, vaikka ne napataan ja ohjelman ei pitäisi sen perusteella jäädä totaalisesti jumiin. En kuitenkaan ole varma toimiiko nuo alert -ilmoitukset oikein tai sammuuko sovellus tietokannassa tapahtueen virheen jälkeen vai jääkö se jostain systä kuitenkin jumiin. Lisäksi käynnistyykö ohjelma tämän jälkeen normaalisti. Nämä asiat jäivät vielä mietityttämään ja näitä pitäisikin jollain tapaa pystyä testaamaan, ennen lopullisen vastauksen saantia.
