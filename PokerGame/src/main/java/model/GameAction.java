/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mykee
 */
public enum GameAction {
    FOLD, CHECK_OR_CALL, RAISE;

    public static GameAction get(int id) {
        return values()[id];
    }
};
