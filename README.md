# FinancialManagement

Sovelluksen avulla käyttäjä voi hallinoida talouttaan pitämällä kirjaa sekä menoista että tuloista. Sovellusta voi käyttää useampi rekisteröitynyt käyttäjä, joilla jokaisella on oma yksilöllinen menojen ja tulojen kirjanpito. Käyttäjä voi lisätä menoja ja tuloja. Viimeiset kymmenen menoa ja tuloa voidaan listata, sekä käyttäjä voi tehdä hakuja, joiden perusteella hän näkee menot määrittelemällään aikavälillä tai haun, jonka perusteella hän näkee kaikki menot ja tulot kategoriottain. 


## Dokumentaatio

[Vaativuusmäärittely](/dokumentointi/vaativuusmaarittely.md)

[Arkkitehtuurikuvaus](/dokumentointi/arkkitehtuuri.md)

[Työaikakirjanpito](/dokumentointi/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/outisa/ot-harjoitustyo/releases)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn jacoco:report`

Kattaavuusraporttia voidaan tarkastella avaamalla selaimella tiedosto _target/jacoco/index.html_.

## Suoritettavan jarin generointi

Komennolla

`mvn package`

generoidaan suoritettava jar-tiedosto _FinancialManagement-1.0-SNAPSHOT.jar_. Tiedosto löytyy hakemistosta _target_.

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/outisa/ot-harjoitustyo/blob/master/Taloudenhallinta/checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

`mvn jxr:jxr checkstyle:checkstyle`

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
