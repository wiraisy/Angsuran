/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.listener;

/**
 *
 * @author diazwiraisy
 */
public interface Confirm {
    
    void Berhasil(String pesan);
    
    void Warning(String pesan);
    
    void Gagal(Throwable t);
    
}
