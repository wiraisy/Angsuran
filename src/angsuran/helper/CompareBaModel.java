/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import angsuran.model.BaModel;
import java.util.Comparator;

/**
 *
 * @author User
 */
public class CompareBaModel implements Comparator<BaModel>{

    @Override
    public int compare(BaModel o1, BaModel o2) {
        if(o1.getNama_bu().equalsIgnoreCase(o2.getNama_bu())){
            return 0;
        }
            
        return 1;
    }
    
}
