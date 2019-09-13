package org.sonar.plugins.utils;

public final class BlitzFinder {

    private final String[] path = {"security", "oauth2", "client", "accessTokenUri"};
    private int position = 1;
    private boolean succ = false;

    public void up(){
        position = position > 1 ? position-1 : 0;
    }

    public boolean isRoot(String key){
        return path[0].equals(key);
    }

    public boolean isSuccessfull(){
        return succ;
    }

    public boolean checkValue(String key) {
        if(position >= path.length){
            return false;
        }
        if(succ){
            return false;
        }
        if (path[position].equals(key)){
            position++;
            succ = position == path.length;
            return true;
        }
        return false;
    }
}
