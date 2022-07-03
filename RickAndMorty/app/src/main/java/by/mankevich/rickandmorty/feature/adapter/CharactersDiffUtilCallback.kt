package by.mankevich.rickandmorty.feature.adapter

import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.feature.base.BaseDiffUtilCallback

class CharactersDiffUtilCallback(oldList: List<CharacterEntity>,
                                 newList: List<CharacterEntity>
) : BaseDiffUtilCallback<CharacterEntity>(oldList, newList) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].status == newList[newItemPosition].status &&
                oldList[oldItemPosition].species == newList[newItemPosition].species &&
                oldList[oldItemPosition].type == newList[newItemPosition].type &&
                oldList[oldItemPosition].gender == newList[newItemPosition].gender &&
                oldList[oldItemPosition].image == newList[newItemPosition].image
    }
}