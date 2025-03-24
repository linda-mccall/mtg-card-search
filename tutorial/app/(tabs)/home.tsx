import { Pressable, StyleSheet } from 'react-native';

import { View } from '@/components/Themed';
import { SearchBar } from "@rneui/themed";
import React, { useState } from 'react';
import { Link, useRouter } from 'expo-router';
import { Text } from 'react-native';
import { Card } from '@/api/server';

export default function Home() {

  const [searchQuery, setSearchQuery] = useState<string>("");
  let [cardSuggestions, setCardSuggestions] = useState<Card[]>();
  const router = useRouter();

  const handleSearchChange = (text: string) => {
    setSearchQuery(text);
    fetch(`/api/cards?search=${text}`)
        .then((response) => response.json())
        .then((json) => setCardSuggestions(json))
  };

  const handleSearchSubmit = () => {
    if (searchQuery.trim()) {
      router.push(`/search-results?search=${searchQuery}`);
    }
  };

  return (
    <div>
    <View style={styles.container}>
      <SearchBar
      placeholder="Type Here"
      onChangeText={(text: string) => handleSearchChange(text)}
      onSubmitEditing={handleSearchSubmit}
      value={searchQuery}
      lightTheme="true"
      >
      </SearchBar>
    </View>

    <ul>
      {cardSuggestions?.map((card) => (
        <li key={card.id}><Link href={{pathname: "/card", params: {id: card.id}}}><Text>{card.name}</Text></Link></li>
      ))}
    </ul>
    </div>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: '80%',
  },
});
