# FinancialManagement

Sovelluksen avulla käyttäjä voi hallinoida talouttaan pitämällä kirjaa sekä menoista että tuloista. Sovellusta voi käyttää useampi rekisteröitynyt käyttäjä, joilla jokaisella on oma yksilöllinen menojen ja tulojen kirjanpito. Käyttäjä voi tehdä hakuja, joiden perusteella hän näkee menot kategorioittain haussa määrittelemällään aikavälillä. 


## Dokumentaatio

[Arkkitehtuurikuvaus](/dokumentointi/arkkitehtuurikuvaus.md)

[Vaativuusmäärittely](/dokumentointi/vaativuusmaarittely.md)

[Työaikakirjanpito](/dokumentointi/tuntikirjanpito.md)



## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn jacoco:report`

Kattaavuusraporttia voidaan tarkastella avaamalla selaimella tiedosto target/jacoco/index.html



### Checkstyle

`mvn jxr:jxr checkstyle:checkstyle`
