import { StyleSheet } from 'react-native';

import { Text, View } from '@/components/Themed';
import { useLocalSearchParams } from 'expo-router';
import React, { useState } from 'react';
import { Card } from '@/api/server';

export default function SearchResults() {
  const { search } = useLocalSearchParams();
  const title: string = `Results for ${search as string}`
    
  let [searchResults, setSearchResults] = useState<Card[]>();
  
  if (searchResults == null) {
    fetch(`/api/cards?search=${search as string}`)
        .then((response) => response.json())
        .then((json) => setSearchResults(json))
    
    if (searchResults == null) {
      searchResults = []
    }
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>{title}</Text>
      <View style={styles.container}>
        {searchResults.map((result) => (
          <img src={result.img}  alt={`Card ${result.id}`} style={styles.image}/>
        ))}
    </View>
    </View>
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
  image: {
    height: "50%",
    alignSelf: 'center',
    resizeMode: 'contain'
 },
});

