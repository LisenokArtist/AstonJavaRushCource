package module.Core.Proxy;

import java.time.LocalDateTime;

public class ServerStatusProxy extends ServerStatus {
    private LocalDateTime lastUpdate = null;
    private int secondsOffset = 0;

    public void setTimeSecondsOffset(int secondsOffset){
        this.secondsOffset = secondsOffset;
    }

    @Override
    public void update(){
        if (lastUpdate == null){
            getFromServer();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now().minusSeconds(secondsOffset);
        if (currentDate.isAfter(lastUpdate)){
            getFromServer();
        }
        else{
            getFromCache();
        }
    }

    private void getFromServer(){
        super.update();
        this.lastUpdate = LocalDateTime.now();
    }

    private void getFromCache(){
        System.out.println("Данные о состоянии сервера получены и кеша");
    }
}
