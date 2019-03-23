# Vaativuusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoituksena on auttaa käyttäjää pitämään kirjaa talouden menoista
ja tuloista kategorioittain. Sovellusta voi käyttää useampi käyttäjä, 
joilla jokaisella on oma yksilöllinen talouden kirjanpito.

## Käyttäjät

Sovelluksella on alussa ainoastaan yksi käyttäjärooli eli _normaali käyttäjä_.
Sovelluksen kehittyessä siihen saatetaan lisätä _pääkäyttäjä_, jolla on 
peruskäyttäjää suuremmat käyttöoikeudet.

## Käyttöliittymäluonnos

Sovellus koostuu alustavasti seitsämästä eri näkymästä:

![Sovellusnäkymä]

Sovellus aukeaa ensimmäisksi kirjautumisnäkymään, josta on mahdollista siirtyä
"luo uusi käyttäjä" -näkymään tai onnistuneen kirjautumisen yhteydessä 
yleisnäkymään. Yleisnäkymässä on mahdollisuus siirtyä lisää uusi tulo tai 
meno lomakkeeseen,
 sekä hakea menoja joltakin aikaväliltä. Onnistuneen uuden tulon tai menon lisäämisen jälkeen palataan yleisnäkymään.
 Jos halutulla aikavälillä on menoja, siirrytään näkymään, jossa on  menot listattuna kategorioittain sekä diagrammi
menojen %-osuuksista kategorioittain. 

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

* Käyttäjä voi luoda järjestelmään käyttäjätunnuksen
  *Käyttäjätunnuksen täytyy olla uniikki ja vähintään 3 merkkiä.

* Käyttäjä voi kirjautua järjestelmään
  * Krjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus aloitusnäkymän kirjautumislomakkeelle.
  * Jos käyttäjää ei löydy järjestelmästä tao tunnus on väärä, ilmoitta järjestelmä tästä aloitusnäkymässä.

### Kirjautumisen jälkeen

* Perusnäkymässä

  * käyttäjä voi siirtyä lomakkeeseen lisäämään uutta tulotapahtumaa.
  * käyttäjä voi siirtyä lomakkeeseen lisäämään uutta menoerää.
  * käyttäjä voi hakea menot joltakin itsevalitsemaltaan aikaväliltä.
    * Haku näyttää yhteenvedon vain käyttäjän luomista menoista.
    * Loppupäivämäärän täytyy olla suurempi kuin alkupäivämäärä.
    * Jos tapahtumia ei löydy aikaväliltä, tulee siitä ilmoitus. 
  * Käyttäjä voi listata kaikki menot ja tulot.
  * Käyttäjä voi kirjautua ulos järjestelmästä.

* Käyttäjä voi lisätä uuden tulotapahtuman
  * Onnistuneen kisäyksen jälkeen palataan perusnäkymään.

* Käyttäjä voi lisätä uuden menoerän
  * Onnistuneen lisäyksen jälkeen palataan perusnäkymään.

* Listausnäkymässä 
  * Käyttäjä näkee kaikki lisäämänsä menot ja tulot aikajärjestyksessä. 
  
* Yhteenvetonäkymässä
  * käyttäjä näkee menot kategorioittain.
  * käyttäjä voi palata perusnäkymään.



## Jatkokehitysideoita

Perusversion jälkeen järjestelmää voidaan täydentää ajan salliessa esimerkiksi seuraavilla toiminnallisuuksilla
 * Haku tuloista tietyllä aikavälillä ja haun ryhmittely kategorioittain.
 * Kategorioiden lisäysmahdollisuus.
 * Haku menoista tietyllä aikavälillä ja haun ryhmittely ostopaikan perusteella.
 * Lisätä tarkempi kuvaus -kenttä, jossa voi tarkemmin kuvata menoerää tai tuloa.
    * Esimerkiksi "Tepon synttärijuhlien tarjoilut ja rekvisiitta"
 * Menon ja tulon lähempi tarkastelu, jossa näytetään kyseiseen menoon tai tuloon liittyvät tiedot.
 * Käyttäjälle mahdollisuus poistaa tili ja siihen liittyvät tiedot. 
 * Käyttäjille salasana, jota vaaditaan kirjautuessa.
 * Useamman käyttäjän versio, jossa voidaan listata myös tulojen ja menojen yhteydessä niitä lisännyt käyttäjä
