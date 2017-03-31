package com.mileto.services;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Security {

	/** Algoritmo utilizado para criptografar senhas **/
	public static String encrypt(String pString, String pAlgho) {
		try {  
			MessageDigest md = MessageDigest.getInstance(pAlgho);  
			BigInteger hash = new BigInteger(1, md.digest(pString.getBytes()));  
			return hash.toString(16);
		} catch(Exception e) {
			return null;
		}	
	}
	
	/** Gera uma chave única. O parametro level indica o quanto única ela será, por segurança **/
	public static String getUniqueKey( int level, int option) {
		String tempo = ( new Long(System.currentTimeMillis() )).toString();
		String unicity = getSecurityCodeAleatorio(level, option);
		return tempo + unicity;
	}
	
	/** Gera uma chave aleatório com o número de caracteres que o cliente quiser **/
    private static String getSecurityCodeAleatorio(int qtde, int option) {
    	RandomIntGenerator rg;
    	String sReturn = "";
        
    	switch (option) {
    		case 0:
    			rg = new RandomIntGenerator('0', '9');
    			break;
    		case 1:
    			rg = new RandomIntGenerator('A', 'Z');
    			break;
    		case 2:	
    			rg = new RandomIntGenerator('a', 'z');
    			break;
    		default:
    			rg = new RandomIntGenerator('A', 'z');
    	}
    	
        for (int i=0; i<qtde; i++) {
                sReturn += (char)rg.draw();
        }
        return sReturn;
    }

}



class RandomIntGenerator{

        public RandomIntGenerator(int l, int h){
                   low = l;
                   high = h;
               }
               public int draw(){
                   int r = low + (int)((high-low+1)*nextRandom());
                   return r;
               }
              public static void main(String[] args){
                  RandomIntGenerator r1 = new RandomIntGenerator(1,10);
                  RandomIntGenerator r2 = new RandomIntGenerator(0,1);
                  int i;
                  for (i = 1;i<=100; i++)
                     System.out.println(r1.draw()+""+r2.draw());
               }
               public static double nextRandom(){
                   int pos = (int)(java.lang.Math.random() * BUFFER_SIZE);
                   double r = buffer[pos];
                   buffer[pos] = java.lang.Math.random();
                   return r;
               }
              private static final int BUFFER_SIZE = 101;
              private static double[] buffer = new double[BUFFER_SIZE];
              static{
                 int i;
                 for (i = 0; i<BUFFER_SIZE; i++)
                     buffer[i] = java.lang.Math.random();
              }
              private int low;
              private int high;
          }                                                                                                                                                                                                           