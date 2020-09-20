import React from 'react'
import { Link, Redirect } from 'react-router-dom'
import { InputText } from 'primereact/inputtext'
import { Button } from 'primereact/button'
import { MultiSelect } from 'primereact/multiselect'
import { InputNumber } from 'primereact/inputnumber'
import { Dropdown } from 'primereact/dropdown'

class CreateCharacter extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            createCharacter: {
                name: '',
                age: 0,
                clazz: '',
                race: '',
                equipment: [],
                spells: [],
            },
            classes: [],
            races: [],
            equipments: [],
            spells: [],
            created: false,
        }

        this.onCharacterClassChange = this.onCharacterClassChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    componentDidMount() {
        fetch('/ded/api/dnd/classes', {
            method: 'GET',
            mode: 'cors', // no-cors, *cors, same-origin
            headers: {
                'Accept': 'application/json',
            },
            credentials: 'same-origin',
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error('Something went wrong!')
            } else {
                return response.json()
            }
        })
        .then((json) => {
            console.log(json)
            this.setState({
                classes: json,
            })
        })
        .catch((err) => {
            console.log(err)
        })

        fetch('/ded/api/dnd/races', {
            method: 'GET',
            mode: 'cors', // no-cors, *cors, same-origin
            headers: {
                'Accept': 'application/json',
            },
            credentials: 'same-origin',
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error('Something went wrong!')
            } else {
                return response.json()
            }
        })
        .then((json) => {
            // console.log(json)
            this.setState({
                races: json,
            })
        })
        .catch((err) => {
            console.log(err)
        })

        fetch('/ded/api/dnd/equipment', {
            method: 'GET',
            mode: 'cors', // no-cors, *cors, same-origin
            headers: {
                'Accept': 'application/json',
            },
            credentials: 'same-origin',
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error('Something went wrong!')
            } else {
                return response.json()
            }
        })
        .then((json) => {
            // console.log(json)
            this.setState({
                equipments: json,
            })
        })
        .catch((err) => {
            console.log(err)
        })
    }

    onCharacterClassChange(e) {
        this.setState(
            {
                createCharacter: Object.assign({}, this.state.createCharacter,
                    { clazz: e.value }),
            },
        )

        // Fetch the spells related to this character
        fetch('/ded/api/dnd/spells/' + e.value, {
            method: 'GET',
            mode: 'cors', // no-cors, *cors, same-origin
            headers: {
                'Accept': 'application/json',
            },
            credentials: 'same-origin',
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error('Something went wrong!')
            } else {
                return response.json()
            }
        })
        .then((json) => {
            // console.log(json)
            this.setState({
                spells: json,
            })
        })
        .catch((err) => {
            console.log(err)
        })
    }

    handleSubmit(event) {

        // Post the character
        fetch('/ded/api/dnd/characters', {
            method: 'POST',
            mode: 'cors', // no-cors, *cors, same-origin
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'same-origin',
            body: JSON.stringify(this.state.createCharacter)
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error('Something went wrong!')
            } else {
                // console.log(this.state.createCharacter)
                this.setState({
                    created: true
                })
            }
        })
        .catch((err) => {
            console.log(err)
        })

        event.preventDefault()
    }

    render() {
        return !this.state.created ? (
            <div>
                <h1>Create Character</h1>
                <form>
                    <label htmlFor="characterName">Name: </label>
                    <InputText id="characterName" className="p-inputtext-sm"
                               type="text" onChange={(e) => this.setState(
                        {
                            createCharacter: Object.assign({},
                                this.state.createCharacter,
                                { name: e.target.value }),
                        })} required />
                    <br /><br />
                    <label htmlFor="characterAge">Age: </label>
                    <InputNumber id="characterAge"
                                 className="p-inputnumber-sm p-inputtext-sm"
                                 onValueChange={(e) => this.setState(
                                     {
                                         createCharacter: Object.assign({},
                                             this.state.createCharacter,
                                             { age: e.value }),
                                     },
                                 )}
                                 mode="decimal"
                                 useGrouping={false} required />
                    <br /><br />
                    <label htmlFor="characterClass">Class: </label>
                    <Dropdown id="characterClass"
                              value={this.state.createCharacter.clazz}
                              options={this.state.classes}
                              onChange={this.onCharacterClassChange}
                              placeholder="Select a Character Class"
                              filter required />

                    <br /><br />
                    <label htmlFor="characterRace">Race: </label>
                    <Dropdown id="characterRace"
                              value={this.state.createCharacter.race}
                              options={this.state.races}
                              onChange={(e) => this.setState(
                                  {
                                      createCharacter: Object.assign({},
                                          this.state.createCharacter,
                                          { race: e.value }),
                                  },
                              )} placeholder="Select a Character Race"
                              filter required />

                    <br /><br />
                    <label htmlFor="characterEquipment">Equipment: </label>
                    <MultiSelect id="characterEquipment"
                                 value={this.state.createCharacter.equipment}
                                 options={this.state.equipments}
                                 onChange={(e) => this.setState(
                                     {
                                         createCharacter: Object.assign({},
                                             this.state.createCharacter, {
                                                 equipment: e.value,
                                             }),
                                     },
                                 )} placeholder="Select a Character Equipment"
                                 filter />

                    <br /><br />
                    <label htmlFor="characterSpells">Spells: </label>
                    <MultiSelect id="characterSpells"
                                 value={this.state.createCharacter.spells}
                                 options={this.state.spells}
                                 onChange={(e) => this.setState(
                                     {
                                         createCharacter: Object.assign({},
                                             this.state.createCharacter, {
                                                 spells: e.value,
                                             }),
                                     },
                                 )} placeholder="Select a Character Spell"
                                 filter />

                    <br /><br />
                    <Button type="submit" label="Create" className="p-button-sm"
                            onClick={this.handleSubmit} />

                </form>
                <br /><br />
                <Link to="/show-characters">Show Characters</Link>
            </div>
        ) : (<Redirect to="/show-characters" />)
    }
}

export default CreateCharacter