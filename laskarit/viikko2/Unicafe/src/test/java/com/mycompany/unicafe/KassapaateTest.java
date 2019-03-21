
package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();  
        kortti = new Maksukortti(1000);
    }
  
    @Test
    public void kassapaatteessaSummaOikeinAlussa() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kassapaatteessaMaukkaatLounaatOikeinAlussa() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassapaatteessaEdullisetLounaatOikeinAlussa() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKateisostoToimiiRiittavallaSummalla() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(400);
        assertEquals(2, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void syoEdullisestiKateisostoToimiiRiittavallaSummalla() {
        paate.syoEdullisesti(250);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void syoMaukkaastiKateisostoPaateSaldoKasvaa() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(400);
        assertEquals(100800, paate.kassassaRahaa());
    }
    @Test
    public void syoEdullisestiKateisostoPaateSaldoKasvaa() {
        paate.syoEdullisesti(240);
        assertEquals(100240, paate.kassassaRahaa());
    }      
    @Test
    public void syoMaukkaastiPalauttaaOikein() {
        assertEquals(200, paate.syoMaukkaasti(600));
    }
    @Test
    public void syoEdullisestiPalauttaaOikein() {
        assertEquals(100, paate.syoEdullisesti(340));
    }    
    @Test
    public void syoMaukkaastiKateisostoSummaEiRiittava() {
        paate.syoMaukkaasti(300);
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(300, paate.syoMaukkaasti(300));
    }
    @Test
    public void syoEdullisestiKateisostoSummaEiRiittava() {
        paate.syoEdullisesti(200);
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(200, paate.syoEdullisesti(200));
    } 
    @Test
    public void syoMaukkaastiKortillaRiittavaSumma() {
        boolean tapahtuma = paate.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
        assertEquals(true, tapahtuma);
    }
    @Test
    public void syoEdullisestiKortillaRiittavaSumma() {
        boolean tapahtuma = paate.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
        assertEquals(true, tapahtuma);
    }
    @Test
    public void syoMaukkaastiKortillaMaaraKasvaa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty()); 
    }
    @Test
    public void syoEdullisestiKortillaMaaraKasvaa() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaEiRiittavaSumma() {
        kortti.otaRahaa(800);
        boolean tapahtuma = paate.syoMaukkaasti(kortti);
        assertEquals(200, kortti.saldo());
        assertEquals(false, tapahtuma);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void syoEdullisestiKortillaEiRiittavaSumma() {
        kortti.otaRahaa(800);
        boolean tapahtuma = paate.syoEdullisesti(kortti);       
        assertEquals(200, kortti.saldo());
        assertEquals(false, tapahtuma);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void lataaRahaaKortilleOnnistuu(){
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(2000, kortti.saldo());
        assertEquals(101000, paate.kassassaRahaa());
    }
    @Test
    public void lataaRahaaKortilleMaaraNegatiivinen(){
        paate.lataaRahaaKortille(kortti, -12);
        assertEquals(1000, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }   
}
