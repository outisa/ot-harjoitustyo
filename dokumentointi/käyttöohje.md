# Käyttöohje

Lataa tiedosto FinancialManagement.jar [täältä](https://github.com/outisa/ot-harjoitustyo/releases).

## Konfigurointi

Sovelluksen mukana tulee kaikki tarvittavat tiedostot.

## Ohjelman käynnistäminen

Mene ensin terminaalissa siihen kansioon, johon latasit tidoston. Windowsissa "terminaali" löytyy Command Prompt nimellä. Aja sitten komento

`java -jar FinancialManagement.jar`

## Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/login.png">

1. Syötä käyttäjänimi **_username_** -kenttään ja kirjaudu sisään painamalla nappia **_login_**.
2. Onnistuneen sisäänkirjautumisen jälkeen siirryt automaattisesti perusnäkymään

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/overview.png">

## Uuden käyttäjätilin luominen

1. Painamalla nappia **_create new user_** kirjautumisnäkymässä, jolloin sovellus avautuu tilinluomisnäkymään

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/newUser.png">

2. Anna haluttu käyttäjänimi **_Username_** kenttään ja paina **_Create user_** -nappia.
3. Jos käyttäjänimi on jo käytössä, saat siitä ilmoituksen. Lisäksi syötteen on oltava väliltä 3-99 ja siinä voi käyttää pelkästään merkkejä A-Z,a-z ja 0-9. Jos syöte ei ole validi, saat siitä myös ilmoituksen. Molemmissa tapauksissa toista kohta 2.
4. Onnistuneen lisäyksen jälkeen siirryt automaattisesti takaisin kirjautumissivulle.

## Uuden menon lisäys

1. Paina perusnäkymässä nappia **_New expense_**, jolloin siirryt menonluomisnäkymään

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/newIncome.png">

2. Valitse menotapahtuman päivämäärä valikosta.
3. Anna maksettu rahamäärä väliltä 0 - 9999999.99.
4. Valitse menoon sopiva kategoria valikosta.
5. Paina nappia **_Add expense_**, jolloin tarkastetaan onko meno jo olemassa.
6. Palataksesi perusnäkymään paina **_Back to overview_** -nappia.

## Uuden tulon lisäys

1. Paina perusnäkymässä nappia **_New Income_**, jolloin siirryt tulonluomisnäkymään

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/newExpenseUI.png">

2. Valitse tulon päivämäärä valikosta.
3. Anna saatu rahamäärä väliltä 0 - 9999999.99.
4. Valitse tuloon sopiva kategoria valikosta.
5. Paina nappia **_Add income_**, jolloin tarkastetaan onko tulo jo olemassa.
6. Palataksesi perusnäkymään paina **_Back to overview_** -nappia.

## Viimeisimpien kymmenen menojen ja tulojen listaus

Paina perusnäkymässä nappia **_List last 10 expenses and incomes_**, jolloin ohjaudut päivämäärän mukaan tuoreimpien menojen ja tulojen listaukseen.

## Menojen haku tietyllä aikavälillä

1. Valitse perusnäkymässä ensin haun aloitusvuosi ja -kuukausi.
2. Valitse sitten lopetuskuukausi ja -vuosi ja paina **_Search_ nappia**.
Esimerkiksi, jos haluat tarkastella tammikuun 2019 menoja, niin valitse aloitusajankohta 2019-01 ja lopetusajankohdaksi 2019-02.
Tällöin haun tulokset ovat väliltä 2019-01-01 ja 2019-01-31.

## Haku kategorioittain: menot ja tulot

Riippuen siitä haluatko tarkastella menoja vai tuloja kategorioittain klikkaa nappia **_Expenses per category_** (menot) 
tai **_Incomes per category_** (tulot). Ohjaudut tämän jälkeen yhteenvetonäkymään haluamastasi tarkastelukohteesta.

## Uloskirjautuminen

Paina nappia **_logout_**, jolloin  siirryt automaattisesti sisäänkirjaantumisnäkymään.

**_HUOM!_** Poikkeuksena haut kategorioittain -näkymistä pitää palata ensin perunäkymään painamalla nappia **_Back to main_** 
ja vasta sitten krjautua ulos painamalla nappia **_logout_**.

## Ohjelman sammutus

Paina oikeassa yläkulmassa olevaa x-merkkiä.
