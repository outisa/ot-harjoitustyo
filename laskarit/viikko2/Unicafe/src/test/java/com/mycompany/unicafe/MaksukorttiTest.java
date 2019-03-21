package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void saldoOikeinLatauksenJalkeen() {
        kortti.lataaRahaa(2500);
        assertEquals("saldo: 25.10", kortti.toString());
    }
    
    @Test
    public void saldoOikeinLadataanNegatiivinenSumma(){
        kortti.lataaRahaa(-111);
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void saldoOikeinNostonJalkeen() {
        kortti.otaRahaa(5);
        assertEquals(5, kortti.saldo());
    }
    
    @Test 
    public void saldoEiMuutuKunRahaaLiianVahan() {
        kortti.otaRahaa(500);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void kortillaOnTarpeeksiRahaa() {
        assertEquals(true, kortti.otaRahaa(5));
        
    }
    
    @Test
    public void kortillaEiOleTarpeeksiRahaa() {
        assertEquals(false, kortti.otaRahaa(11));
    }
    
   
}
