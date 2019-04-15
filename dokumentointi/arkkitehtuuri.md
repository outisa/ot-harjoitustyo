# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne mukailee kolmitasoista kerrosarkkitehtuuria ja koodin pakkausrakenne on seuraava:

<img src ="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkauskaavio1.png" width=200>

Pakkaus financialmanagement.ui sisältää JavaFX:n avulla toteutetun graaffisen käyttöliittymän koodin. Sovelluslogiikasta vastaava koodi on tehty pakkaukseen financialmanagement.domain ja financialmanagement.dao pakkauksessa oleva koodi vastaa tiedon pysyväistallennuksesta.

## Käyttöliittymä

## Sovelluslogiikka

Alla ohjelman rakenne pakkauskaaviona, joka kuvaa FinancialManagementServicen ja ohjelman muiden osien suhdetta:

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/Pakkauskaavio2.png" width=450>

## Tietojen pysyväistallennus

Pakkauksen financialmanagement.dao luokat SQLUserDao, SQLExpenseDao ja SQLIncomeDao huolehtivat tietojen tallennuksesta tietokantaan sekä tiedon hausta tietokannasta.

Luokat on eristetty rajapintojen UserDao, ExpenseDao ja IncomeDao taakse, jolloin sovelluslogiikka ei käytä luokkia suoraan.
Sovelluslogiikkaa testatessa käytetään valeluokkia, jotka tallentavat testissä käytettävät tiedot keskusmuistiin tietokantojen sijaan.

### Tietokanta

Tietokanta koostuu kolmesta eri taulusta: käyttäjätili, menot ja tulot.

[Konfiguraatiotiedosto config.properties](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/src/main/resources/config.properties) määrittelee sekä varsinaisen tietokannan että testitietokannan nimet. Testitietokantaa käytetään vain ajettaessa testejä ja muulloin se on tyhjä. Konfiguraatiotiedosto sijaitsee Taloudenhallinta/src/main/resources/ -kansiossa.

### Päätoiminnallisuudet

#### Käyttäjän kirjautuminen

Kirjautumisnäkymässä kirjoitetaan ensin syötekenttään käyttäjätunnus, jonka jälkeen klikattaessa nappia _loginButton_ sovelluksen kontrolli etenenee seuraavasti: 

<img src = "https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/loginKaavio.png" width= 800>

_LoginButton_ painikkeen painamiseen reagoiva [tapahtumankäsittelijä](https://github.com/outisa/ot-harjoitustyo/blob/36efcc5ed06e020438582a9475961b1c18970fef/Taloudenhallinta/src/main/java/financialmanagement/ui/FinancialManagementUi.java#L85) kutsuu sovelluslogiikan FinancialManagementService luokan metodia [login](https://github.com/outisa/ot-harjoitustyo/blob/36efcc5ed06e020438582a9475961b1c18970fef/Taloudenhallinta/src/main/java/financialmanagement/domain/FinancialManagementService.java#L54), jolle annetaan parametriksi syötekenttään syötetty käyttäjätunnus. Sovelluslogiikka selvittää UserDaon avulla käyttäjätunnuksen olemassaolon. Tapahtuman kululla on kaksi vaihtoehtoista reittiä: Käyttäjätunnuksen ollessa olemassa UserDao palauttaa ensin käyttäjän, jonka seurauksena FinancialManagementService palauttaa arvon true ja käyttäjälle vaihdetaan näkymäksi _mainScene_, eli valikko erilaisia nappeja sovelluksen toimintoihin. Jos käyttäjää ei löydy, eli UserDao on palauttanut arvon null, niin käyttäjälle näytetään edelleen _loginScene_ eli kirjautumissivu virheviestin kanssa. 

## Ohjelman rakenteeseen jääneet heikkoudet
