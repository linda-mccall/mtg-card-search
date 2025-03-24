import { StyleSheet } from 'react-native';

import { Text, View } from '@/components/Themed';
import { useLocalSearchParams } from 'expo-router';
import React, { useState } from 'react';
import { Card } from '@/api/server';

export default function CardPage() {
  const { id  } = useLocalSearchParams();
  let [card, setCard] = useState<Card>();
  
  if (card == null) {
    fetch(`/api/cards/${id as string}`)
    .then((response) => response.json())
    .then((json) => setCard(json))
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>{card?.name}</Text>
      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" />
      <View style={styles.container}>
        <img src={card?.img} style={styles.image}/>
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
  separator: {
    marginVertical: 30,
    height: 1,
    width: '80%',
  },
  image: {
    height: "80%",
    alignSelf: 'center',
    resizeMode: 'contain'
 },
});