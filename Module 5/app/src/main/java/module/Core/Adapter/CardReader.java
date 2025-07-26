package module.Core.Adapter;

public class CardReader implements USB {
    private MicroSD card;

    public CardReader(MicroSD card){
        this.card = card;
    }

    @Override
    public void connectWithUsbCable() {
        this.card.insert();
        this.card.copyData();
    }
}
