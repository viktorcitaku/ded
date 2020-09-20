import React from 'react'
import { Link } from 'react-router-dom'
import { DataTable } from 'primereact/datatable'
import { Column } from 'primereact/column'

class ShowCharacter extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            userCharacters: [],
        }
    }

    componentDidMount() {
        // Fetch characters
        fetch('/ded/api/dnd/characters', {
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
            let finalList = []
            for (var j of json) {
                finalList.push({
                    name: j.name,
                    age: j.age,
                    clazz: j.clazz,
                    race: j.race,
                    equipment: JSON.stringify(j.equipment),
                    spells: JSON.stringify(j.spells),
                })
            }
            this.setState({
                userCharacters: finalList,
            })
        })
        .catch((err) => {
            console.log(err)
        })
    }

    render() {
        return (
            <div>
                <h1>Show Character</h1>
                <br /><br />
                <div>
                    <div className="card">
                        <DataTable value={this.state.userCharacters} paginator
                                   paginatorTemplate="CurrentPageReport FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink"
                                   currentPageReportTemplate="Showing {first} to {last} of {totalRecords}"
                                   rows={10}
                        >
                            <Column field="name" header="Name" />
                            <Column field="age" header="Age" />
                            <Column field="clazz" header="Class" />
                            <Column field="race" header="Race" />
                            <Column field="equipment" header="Equipment" />
                            <Column field="spells" header="Spells" />
                        </DataTable>
                    </div>
                </div>
                <br /><br />
                <Link to="/create-character">Create Character</Link>
            </div>
        )
    }
}

export default ShowCharacter