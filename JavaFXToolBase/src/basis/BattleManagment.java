/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tezuro
 */
public class BattleManagment {
    final static private Map<AllianceBanner,List<Player>> onScreenPlayer = new HashMap<>();
    
    private BattleManagment(){
    }
    
    public static void init(){
        for(AllianceBanner g:AllianceBanner.values()){
            onScreenPlayer.put(g, new ArrayList<Player>());
        }
    }
    
    public static void registerPlayer(Player newPlayer){
        onScreenPlayer.get(newPlayer.alliance).add(newPlayer);
    }
    
    public static void deregisterPlayer(Player oldPlayer){
        onScreenPlayer.get(oldPlayer.alliance).remove(oldPlayer);
    }
    
    public static List<Player> getFriends(Player player){
        List<Player> friends = new ArrayList(onScreenPlayer.get(player.alliance));
        return friends;
    }
        public static List<Player> getFriendsExcluding(Player player){
        List<Player> friends = new ArrayList(onScreenPlayer.get(player.alliance));
        friends.remove(player);
        return friends;
    }
    public static List<Player> getFoe(Player player){
        List<Player> foes = new ArrayList();
        for(AllianceBanner g:AllianceBanner.values()){
            if(!g.equals(player.alliance)){
                foes.addAll(onScreenPlayer.get(g));
            }
        }
        return foes;
    }
    
}
