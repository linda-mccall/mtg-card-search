package com.mtg.cardsearch.service

import com.mtg.cardsearch.dto.SaveCardDto
import com.mtg.cardsearch.entity.List
import com.mtg.cardsearch.exception.UnauthorizedException
import com.mtg.cardsearch.repository.CardRepository
import com.mtg.cardsearch.repository.ListRepository
import com.mtg.cardsearch.repository.UserRepository
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
class ListService(private val userRepository: UserRepository,
                  private val listRepository: ListRepository,
                  private val cardRepository: CardRepository) {

    fun createList(email: String, listName: String): List {
        val user = userRepository.findFirstByEmail(email)
        val list = List(null, listName, user, Collections.emptySet())
        return listRepository.save(list)
    }

    fun update(id:Int, email: String, listName: String): List? {
        val listOptional = listRepository.findById(id)
        if (listOptional.isPresent) {
            val list = listOptional.get()
            if (StringUtils.equalsIgnoreCase(list.user.email, email)) {
                list.name = listName
                return listRepository.save(list)
            } else {
                throw UnauthorizedException("User does not own requested List")
            }
        }
        return null
    }

    fun deleteList(id: Int, email: String) {
        val listOptional = listRepository.findById(id)
        if (listOptional.isPresent) {
            val list = listOptional.get()
            if (StringUtils.equalsIgnoreCase(list.user.email, email)) {
                listRepository.deleteById(id)
            } else {
                throw UnauthorizedException("User does not own requested List")
            }
        }
    }

    fun saveCardToList(saveCardDto: SaveCardDto, email: String): List? {
        val cardOptional = cardRepository.findById(saveCardDto.cardId)
        val listOptional = listRepository.findById(saveCardDto.listId)

        return if (listOptional.isPresent && cardOptional.isPresent) {
            if (StringUtils.equalsIgnoreCase(listOptional.get().user.email, email)) {
                val card = cardOptional.get()
                card.lists = card.lists.plus(listOptional.get())
                cardRepository.save(card)
                return listOptional.get()
            } else {
                throw UnauthorizedException("User does not own requested List")
            }
        } else {
            null
        }
    }

    fun removeCardFromList(listId: Int, cardId: Int, email: String): List? {
        val cardOptional = cardRepository.findById(cardId)
        var listOptional = listRepository.findById(listId)

        return if (listOptional.isPresent && cardOptional.isPresent) {
            if (StringUtils.equalsIgnoreCase(listOptional.get().user.email, email)) {
                var card = cardOptional.get()
                card.lists = card.lists.minus(listOptional.get())
                cardRepository.save(card)
                return listOptional.get()
            } else {
                throw UnauthorizedException("User does not own requested List")
            }
        } else {
            null
        }
    }
}
