package module.Core.Collections;

public class GenericHashSet<E> {
    private GenericHashMap<Integer, E> map;

    public GenericHashSet() {
        map = new GenericHashMap<>();
    }

    /**
     * Добавляет элемент в список или
     * перезаписывает существующий
     * @param element Добавляемый элемент
     */
    public void put(E element) {
        map.put(element.hashCode(), element);
    }

    /**
     * Получает элемент из списка по индексу
     * @param index Номер элемента в списке
     * @return Значение в списке или null
     */
    public E get(int index){
        return map.getByIndex(index);
    }

    /**
     * Удаляет элемент из списка
     * @param element Элемент, который нужно удалить
     */
    public void remove(E element){
        map.remove(element.hashCode());
    }

    /**
     * Размер списка
     * @return Integer значение размера коллекции
     */
    public int size(){
        return map.size();
    }
}
