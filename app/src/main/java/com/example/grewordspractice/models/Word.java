package com.example.grewordspractice.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "saved_words")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String definitions;

    private String synonym;

    private boolean showInTenMin;

    private boolean showNextWeek;

    private boolean showNextDay;

    private Date markedDate;

    public Word() {
    }

    public Word(String word, String definitions, String synonym) {
        this.word = word;
        this.definitions = definitions;
        this.synonym = synonym;
    }

    public Word(String word, String definitions, String synonym, boolean showInTenMin, boolean showNextWeek, boolean showNextDay, Date markedDate) {
        this.word = word;
        this.definitions = definitions;
        this.synonym = synonym;
        this.showInTenMin = showInTenMin;
        this.showNextWeek = showNextWeek;
        this.showNextDay = showNextDay;
        this.markedDate = markedDate;
    }

    public Word(int id, String word, String definitions, String synonym, boolean showInTenMin, boolean showNextWeek, boolean showNextDay, Date markedDate) {
        this.id = id;
        this.word = word;
        this.definitions = definitions;
        this.synonym = synonym;
        this.showInTenMin = showInTenMin;
        this.showNextWeek = showNextWeek;
        this.showNextDay = showNextDay;
        this.markedDate = markedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public boolean isShowInTenMin() {
        return showInTenMin;
    }

    public void setShowInTenMin(boolean showInTenMin) {
        this.showInTenMin = showInTenMin;
    }

    public boolean isShowNextWeek() {
        return showNextWeek;
    }

    public void setShowNextWeek(boolean showNextWeek) {
        this.showNextWeek = showNextWeek;
    }

    public boolean isShowNextDay() {
        return showNextDay;
    }

    public void setShowNextDay(boolean showNextDay) {
        this.showNextDay = showNextDay;
    }

    public Date getMarkedDate() {
        return markedDate;
    }

    public void setMarkedDate(Date markedDate) {
        this.markedDate = markedDate;
    }
}

/* {
        "id": "jargon",
        "metadata": {
                "operation": "retrieve",
                "provider": "Oxford University Press",
                "schema": "RetrieveEntry"
                    },
        "results": [
            {
                "id": "jargon",
                "language": "en-gb",
                "lexicalEntries": [
                    {
                        "derivatives": [
                            {
                            "id": "jargonic",
                            "text": "jargonic"
                            }
                        ],
                        "entries": [
                            {
                            "etymologies": [
                                "late Middle English (originally in the sense ‘twittering, chattering’, later ‘gibberish’): from Old French jargoun, of unknown origin. The main sense dates from the mid 17th century"
                            ],
                            "grammaticalFeatures": [
                                {
                                "id": "mass",
                                "text": "Mass",
                                "type": "Countability"
                                }
                            ],
                            "homographNumber": "100",
                            "senses": [
                                {
                                "definitions": [
                                    "special words or expressions used by a profession or group that are difficult for others to understand"
                                ],
                                "domains": [
                                    {
                                    "id": "linguistics",
                                    "text": "Linguistics"
                                    }
                                ],
                                "examples": [
                                    {
                                    "text": "legal jargon"
                                    }
                                ],
                                "id": "m_en_gbus0529380.005",
                                "shortDefinitions": [
                                    "special words or expressions used by profession or group that are difficult for others"
                                ],
                                "subsenses": [
                                {
                                    "definitions": [
                                        "a form of language regarded as barbarous, debased, or hybrid."
                                    ],
                                    "id": "m_en_gbus0529380.008",
                                    "registers": [
                                        {
                                        "id": "archaic",
                                        "text": "Archaic"
                                        }
                                    ],
                                    "shortDefinitions": [
                                        "form of language regarded as barbarous or hybrid"
                                    ]
                                }
                                ],
                                "synonyms": [
                                {
                                "language": "en",
                                "text": "specialized language"
                                },
                                {
                                "language": "en",
                                "text": "technical language"
                                },
                                {
                                "id": "slang",
                                "language": "en",
                                "text": "slang"
                                },
                                {
                                "id": "cant",
                                "language": "en",
                                "text": "cant"
                                },
                                {
                                "id": "idiom",
                                "language": "en",
                                "text": "idiom"
                                },
                                {
                                "id": "argot",
                                "language": "en",
                                "text": "argot"
                                },
                                {
                                "id": "patter",
                                "language": "en",
                                "text": "patter"
                                },
                                {
                                "id": "patois",
                                "language": "en",
                                "text": "patois"
                                },
                                {
                                "id": "vernacular",
                                "language": "en",
                                "text": "vernacular"
                                }
                                ],
                                "thesaurusLinks": [
                                {
                                "entry_id": "jargon",
                                "sense_id": "t_en_gb0008283.001"
                                }
                                ]
                                }
                            ]
                            }
                            ],
                        "language": "en-gb",
                        "lexicalCategory": {
                            "id": "noun",
                            "text": "Noun"
                        },
                        "pronunciations": [
                        {
                            "audioFile": "http://audio.oxforddictionaries.com/en/mp3/jargon_gb_1.mp3",
                            "dialects": [
                                "British English"
                            ],
                            "phoneticNotation": "IPA",
                            "phoneticSpelling": "ˈdʒɑːɡ(ə)n"
                        }
                        ],
                        "text": "jargon"
                    }
                ],
                "type": "headword",
                "word": "jargon"
            },
            {
                "id": "jargon",
                "language": "en-gb",
                "lexicalEntries": [
                        {
                        "entries": [
                            {
                            "etymologies": [
                                "mid 18th century: from French, from Italian giargone; probably ultimately related to zircon"
                            ],
                            "grammaticalFeatures": [
                                {
                                "id": "mass",
                                "text": "Mass",
                                "type": "Countability"
                                }
                            ],
                            "homographNumber": "200",
                            "senses": [
                                {
                                "definitions": [
                                    "a translucent, colourless, or smoky gem variety of zircon."
                                ],
                                "domains": [
                                    {
                                    "id": "mineral",
                                    "text": "Mineral"
                                    }
                                ],
                                "id": "m_en_gbus0529390.010",
                                "shortDefinitions": [
                                    "translucent, colourless, or smoky gem variety of zircon"
                                ]
                                }
                            ],
                            "variantForms": [
                            {
                            "pronunciations": [
                            {
                            "phoneticNotation": "IPA",
                            "phoneticSpelling": "dʒɑːˈɡuːn"
                            }
                            ],
                            "text": "jargoon"
                            }
                            ]
                            }
                        ],
                        "language": "en-gb",
                        "lexicalCategory": {
                            "id": "noun",
                            "text": "Noun"
                        },
                        "pronunciations": [
                            {
                            "audioFile": "http://audio.oxforddictionaries.com/en/mp3/jargon_gb_1.mp3",
                            "dialects": [
                                "British English"
                            ],
                            "phoneticNotation": "IPA",
                            "phoneticSpelling": "ˈdʒɑːɡ(ə)n"
                            }
                        ],
                        "text": "jargon"
                        }
                ],
                "type": "headword",
                "word": "jargon"
            }
        ],
        "word": "jargon"
} */
