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

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/kayttoliittymaluonnos.jpg" width="850">

Sovellus aukeaa ensimmäiseksi kirjautumisnäkymään, josta on mahdollista siirtyä
"luo uusi käyttäjä" -näkymään tai onnistuneen kirjautumisen yhteydessä 
yleisnäkymään. Yleisnäkymässä on mahdollisuus siirtyä lisää uusi tulo tai 
meno lomakkeeseen. Onnistuneen uuden tulon tai menon lisäämisen jälkeen pysytään tulo- tai menonäkymässä ja käyttäjä saa viestin onnistuneesta lisäyksestä. Jos tarkalleen samoilla tiedoilla oleva tulo tai meno on jo olemassa, saadaan virheilmoitus. Käyttäjä voi halutessaan siirtyä päänäkymään. 
 Lisäksi yleisnäkymässä voidaan hakea menoja joltakin aikaväliltä. Jos halutulla aikavälillä on menoja, siirrytään näkymään, jossa on  menot listattuna kategorioittain sekä diagrammi menojen %-osuuksista kategorioittain. Päänäkymästä pääsee myös listausnäkymään, jossa on viimeisimmät kymmenen menoa ja tuloa listattuna. 

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

* Käyttäjä voi luoda järjestelmään käyttäjätunnuksen
  *Käyttäjätunnuksen täytyy olla uniikki ja vähintään 3 merkkiä.

* Käyttäjä voi kirjautua järjestelmään
  * Krjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus aloitusnäkymän kirjautumislomakkeelle.
  * Jos käyttäjää ei löydy järjestelmästä tai tunnus on väärä, ilmoittaa järjestelmä tästä aloitusnäkymässä.

### Kirjautumisen jälkeen

* Perusnäkymässä

  * käyttäjä voi siirtyä lomakkeeseen lisäämään uutta tulotapahtumaa.
  * käyttäjä voi siirtyä lomakkeeseen lisäämään uutta menoerää.
  * käyttäjä voi hakea menot joltakin itsevalitsemaltaan aikaväliltä.
    * Haku näyttää yhteenvedon vain käyttäjän luomista menoista.
    * Loppupäivämäärän täytyy olla suurempi kuin alkupäivämäärä.
    * Jos tapahtumia ei löydy aikaväliltä, tulee siitä ilmoitus. 
  * Käyttäjä voi siirtyä uusimpien kymmenen menoerän ja tulon listaukseen.
  * Käyttäjä voi kirjautua ulos järjestelmästä.

* Tulonlisäysnäkymä
  * Käyttäjä voi lisätä uuden tulotapahtuman 
    * Onnistuneen lisäyksen jälkeen pysytään lisäysnäkymässä ja käyttäjä saa ilmoituksen onnistuneesta lisäyksestä.
    * Jos tarkalleen samoilla tiedoilla oleva tulo on jo olemassa, saa käyttäjä virheiloituksen ja lisäys ei onnistu.
  * Käyttäjä voi palata perusnäkymään.
  * Käyttäjä voi kirjautua ulos.
  
* Menonlisäysnäkymä  
  * Käyttäjä voi lisätä uuden menoerän
    * Onnistuneen lisäyksen jälkeen pysytään lisäysnäkymässä ja käyttäjä saa ilmoituksen onnistuneesta lisäyksestä.
    * Jos tarkalleen samoilla tiedoilla oleva meno on jo olemassa, saa käyttäjä virheiloituksen ja lisäys ei onnistu.
  * Käyttäjä voi palata perusnäkymään.
  * Käyttäjä voi kirjautua ulos.

* Listausnäkymässä 
  * Käyttäjä näkee kymmenen tuoreinta lisäämänsä menoa ja tuloa.
  * Käyttäjä voi palata perusnäkymään.
  * Käyttäjä voi kirjautua ulos.
  
* Yhteenvetonäkymässä
  * käyttäjä näkee menot kategorioittain.
  * käyttäjä voi palata perusnäkymään.
  * Käyttäjä voi kirjautua ulos.


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
