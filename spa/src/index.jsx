import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import 'primereact/resources/themes/saga-blue/theme.css'
import 'primereact/resources/primereact.min.css'
import 'primeicons/primeicons.css'
import CreateCharacter from './routes/CreateCharacter'
import ShowCharacter from './routes/ShowCharacter'
import Register from './routes/Register'

ReactDOM.render(
    <Router basename="/ded">
        <Switch>
            <Route path="/create-character" component={CreateCharacter} />
            <Route path="/show-characters" component={ShowCharacter} />
            <Route path="/" component={Register} />
        </Switch>
    </Router>,
    document.getElementById('root'),
)
