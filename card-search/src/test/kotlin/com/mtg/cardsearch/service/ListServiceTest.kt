package com.mtg.cardsearch.service

import com.mtg.cardsearch.dto.SaveCardDto
import com.mtg.cardsearch.entity.Card
import com.mtg.cardsearch.entity.List
import com.mtg.cardsearch.entity.Rarity
import com.mtg.cardsearch.entity.User
import com.mtg.cardsearch.exception.UnauthorizedException
import com.mtg.cardsearch.repository.CardRepository
import com.mtg.cardsearch.repository.ListRepository
import com.mtg.cardsearch.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class ListServiceTest {

    val userRepository = Mockito.mock(UserRepository::class.java)
    val listRepository = Mockito.mock(ListRepository::class.java)
    val cardRepository = Mockito.mock(CardRepository::class.java)

    @Test
    fun createList() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        Mockito.`when`(listRepository.save(any())).thenReturn(list)
        Mockito.`when`(userRepository.findFirstByEmail(anyString())).thenReturn(user)
        val listService = ListService(userRepository, listRepository, cardRepository)
        listService.createList("email@email.com", "list")
        verify(listRepository, times(1)).save(any())
    }

    @Test
    fun updateListValidUser() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        val listService = ListService(userRepository, listRepository, cardRepository)
        listService.update(1, "email@email.com", "list")
        verify(listRepository, times(1)).save(any())
    }

    fun updateListInvalidUser() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        val listService = ListService(userRepository, listRepository, cardRepository)
        assertThrows<UnauthorizedException> {
            listService.update(1, "otherEmail@email.com", "list")
        }
        verify(listRepository, never()).save(any())
    }

    @Test
    fun deleteListValidUser() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        val listService = ListService(userRepository, listRepository, cardRepository)
        listService.deleteList(1, "email@email.com")
        verify(listRepository, times(1)).deleteById(any())
    }

    @Test
    fun deleteListInvalidUser() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        val listService = ListService(userRepository, listRepository, cardRepository)
        assertThrows<UnauthorizedException> {
            listService.deleteList(1, "otherEmail@email.com")
        }
        verify(listRepository, never()).deleteById(any())
    }

    @Test
    fun saveCardToListValidUser() {
        val saveCard = SaveCardDto(1, 1)
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.of(card))
        val listService = ListService(userRepository, listRepository, cardRepository)
        listService.saveCardToList(saveCard, "email@email.com")
        verify(cardRepository, times(1)).save(any())
    }

    @Test
    fun saveCardToListCardNotFound() {
        val saveCard = SaveCardDto(1, 1)
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.empty())
        val listService = ListService(userRepository, listRepository, cardRepository)
        var saved = listService.saveCardToList(saveCard, "email@email.com")
        verify(cardRepository, never()).save(any())
        Assertions.assertEquals(null, saved)
    }

    @Test
    fun saveCardToListWhenListNotFound() {
        val saveCard = SaveCardDto(1, 1)
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.empty())
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.of(card))
        val listService = ListService(userRepository, listRepository, cardRepository)
        var saved = listService.saveCardToList(saveCard, "email@email.com")
        verify(cardRepository, never()).save(any())
        Assertions.assertEquals(null, saved)
    }

    @Test
    fun saveCardToListInvalidUser() {
        val saveCard = SaveCardDto(1, 1)
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.of(card))
        val listService = ListService(userRepository, listRepository, cardRepository)
        assertThrows<UnauthorizedException> {
            listService.saveCardToList(saveCard, "other@email.com")
        }
        verify(cardRepository, never()).save(any())
    }

    @Test
    fun removeCardFromListValidUser() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.of(card))
        val listService = ListService(userRepository, listRepository, cardRepository)
        listService.removeCardFromList(1,1, "email@email.com")
        verify(cardRepository, times(1)).save(any())
    }

    @Test
    fun removeCardFromListCardNotFound() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.empty())
        val listService = ListService(userRepository, listRepository, cardRepository)
        var removed = listService.removeCardFromList(1,1, "email@email.com")
        verify(cardRepository, never()).save(any())
        Assertions.assertEquals(null, removed)
    }

    @Test
    fun removeCardFromListWhenListNotFound() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.empty())
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.of(card))
        val listService = ListService(userRepository, listRepository, cardRepository)
        var removed = listService.removeCardFromList(1,1, "email@email.com")
        verify(cardRepository, never()).save(any())
        Assertions.assertEquals(null, removed)
    }

    @Test
    fun removeCardFromListInvalidUser() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        val list = List(id = 1, name = "list", user = user, cards = Collections.emptySet())
        val rarity = Rarity(id = 1, name = "common")
        val card = Card(id = 1, cardManaCost = 0, digitalOnly = false, name = "Forest", rarity = rarity, legalities = Collections.emptySet(),
                lists = Collections.emptySet(), subtypes = Collections.emptySet(), types = Collections.emptySet())
        Mockito.`when`(listRepository.findById(any())).thenReturn(Optional.of(list))
        Mockito.`when`(cardRepository.findById(any())).thenReturn(Optional.of(card))
        val listService = ListService(userRepository, listRepository, cardRepository)
        assertThrows<UnauthorizedException> {
            listService.removeCardFromList(1,1, "other@email.com")
        }
        verify(cardRepository, never()).save(any())
    }
}
