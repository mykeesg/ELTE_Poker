/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import client.Client;

/**
 *
 * @author iron2414
 */
public class PlayerAction {

    private int action;
    private int raiseAmount;
    private String name;

    public PlayerAction(int action) {
        this.action = action;
        this.raiseAmount = 0;
        this.name = Client.name;
    }

    public PlayerAction(int action, int raiseAmount) {
        this.action = action;
        this.raiseAmount = raiseAmount;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getRaiseAmount() {
        return raiseAmount;
    }

    public void setRaiseAmount(int raiseAmount) {
        this.raiseAmount = raiseAmount;
    }

}
