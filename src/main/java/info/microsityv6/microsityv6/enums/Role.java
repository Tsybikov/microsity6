/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.enums;

/**
 *
 * @author Panker-RDP
 */
public enum Role {
    ADMIN("Администратор"),SUPERUSER("Менеджер"), USER("Пользователь"),CORP_USER("Корпоративный клиент");
    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
