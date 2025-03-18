import { Feather } from "@expo/vector-icons";
import { Link } from 'expo-router';
import { Pressable } from "react-native";
import { Colors } from "react-native/Libraries/NewAppScreen";

type CardSuggestProps = {
    cardName: string;
    id: string;
  };
  
  export default function SearchSuggestion({ cardName, id }: { cardName: string, id: string }) : React.ReactNode {
    return <Link href="/card">{cardName}</Link>;
  }  