/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wiraisy
 */

@Entity
@Table
public class Userku implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Column
    private Long iduser;
    @Getter
    @Setter
    @Column
    private String username;
    @Getter
    @Setter
    @Column
    private String password;
    @Getter
    @Setter
    @Column
    private String previllage;
    @Getter
    @Setter
    @Column
    private boolean managementba = Boolean.FALSE;
    @Getter
    @Setter
    @Column
    private boolean managementcicilan = Boolean.FALSE;
    @Getter
    @Setter
    @Column
    private boolean olahpembayaran = Boolean.FALSE;
    @Getter
    @Setter
    @Column
    private boolean managementuser = Boolean.FALSE;

    public Userku() {
    }
    
    

 
}
