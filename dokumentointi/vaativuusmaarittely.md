# Vaativuusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoituksena on auttaa käyttäjää pitämään kirjaa talouden menoista
ja tuloista kategorioittain. Sovellusta voi käyttää useampi käyttäjä, 
joilla jokaisella on oma yksilöllinen talouden kirjanpito.

## Käyttäjät

Sovelluksella on vain yksi käyttäjärooli eli niin kutsuttu _normaali käyttäjä_.

## Käyttöliittymäluonnos

Sovellus koostuu kahdeksasta eri näkymästä (Käyttöohjeissa on osasta näkymiä paremmat kuvat):

<img src="https://github.com/outisa/ot-harjoitustyo/blob/master/dokumentointi/kuvat/kayttoliittymaluonnos2.jpg" width=900>

Sovellus aukeaa ensimmäiseksi kirjautumisnäkymään, josta on mahdollista siirtyä
"luo uusi käyttäjä" -näkymään tai onnistuneen kirjautumisen yhteydessä 
yleisnäkymään. Yleisnäkymässä on mahdollisuus siirtyä lisää uusi tulo tai 
meno lomakkeeseen. Onnistuneen uuden tulon tai menon lisäämisen jälkeen pysytään tulo- tai menonäkymässä ja käyttäjä saa viestin onnistuneesta lisäyksestä. Jos tarkalleen samoilla tiedoilla oleva tulo tai meno on jo olemassa, saadaan virheilmoitus. Käyttäjä voi halutessaan siirtyä päänäkymään tai kirjautua ulos.  
  Lisäksi yleisnäkymässä voidaan hakea menoja joltakin aikaväliltä. Tästä siirrytään näkymään, jossa on menot listattuna kyseisellä aikavälillä. Päänäkymästä pääsee myös listausnäkymään, jossa on viimeisimmät kymmenen menoa ja tuloa listattuna. Lisäksi päänäkymästä on napit listaus kategorioittain sekä menoille ja tuloille. Näistä molemmille avautuu nappia painamalla näkymä, jossa on pylväsdiagrammi kategorioille ja niiden prosenttiosuus kokonaismäärästä. Sen lisäksi käyttäjä näkee listamuodossa käytetyt (tai saadut) rahamäärät kategorioittain. 

## Loppupalautuksen tarjoama toiminnallisuus

### Ennen kirjautumista

* Käyttäjä voi luoda järjestelmään käyttäjätunnuksen
  *Käyttäjätunnuksen täytyy olla uniikki ja vähintään 3 merkkiä, mutta maksimissaan 99 merkkiä.

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
  * Käyttäjä voi siirtyä tarkastelemaan menoja kategorioittain.
  * Käyttäjä voi siirtyä tarkastelemaan tuloja kategorioittain.
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
  
* Yhteenvetonäkymässä jollekin aikavälille
  * käyttäjä näkee menot haluamaltaan aika väliltä
  * Käyttäjä voi palata perusnäkymään
  * käyttäjä voi kirjautua ulos
  
* Yhteenvetonäkymässä menot kategorioittain
  * käyttäjä näkee menot kategorioittain.
  * käyttäjä voi palata perusnäkymään.

* Yhteenvetonäkymässä tulot kategorioittain
  * käyttäjä näkee tulot kategorioittain.
  * käyttäjä voi palata perusnäkymään.
  
## Jatkokehitysideoita

Seuraavissa versioissa järjestelmää voidaan täydentää esimerkiksi seuraavilla toiminnallisuuksilla
 * Haku tuloista tietyllä aikavälillä.
 * Kategorioiden lisäysmahdollisuus..
 * Lisätä tarkempi kuvaus -kenttä, jossa voi tarkemmin kuvata menoerää tai tuloa.
    * Esimerkiksi "Kuntosalikortti"
 * Menon ja tulon lähempi tarkastelu, jossa näytetään kyseiseen menoon tai tuloon liittyvät tiedot.
 * Käyttäjälle mahdollisuus poistaa tili ja siihen liittyvät tiedot. 
 * Käyttäjille salasana, jota vaaditaan kirjautuessa ja mahdollisuus muokata salasanaa.
 * Menojen ja tulojen muokkaustoiminnot.
 * Useamman käyttäjän versio, jossa voidaan listata myös tulojen ja menojen yhteydessä niitä lisännyt käyttäjä

