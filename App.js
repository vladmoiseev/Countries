import React, { useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';

const Container = styled.div`
    background-image: url('/karta.jpg');
    background-size: cover;
    background-position: center;
    height: 100vh;
    width: 100vw;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    overflow: hidden;
    margin: 0;
    padding: 0;
    box-sizing: border-box;
`;

const HeaderContainer = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    background-color: transparent;
    padding: 10px;
    box-sizing: border-box;
    z-index: 1;
`;

const ContentContainer = styled.div`
    margin-top: 60px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    border-radius: 10px;
    background-color: #00AAA9;
    padding: 20px;
    width: 50%;
    border: 2px solid black;
    box-sizing: border-box;
`;

const Title = styled.h1`
    font-size: 4rem;
    margin-bottom: 20px;
`;

const ButtonContainer = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 50px;
`;

const Input = styled.input`
    padding: 15px 30px;
    margin-right: 10px;
    font-size: 24px;
    border: 2px solid #000;
    border-radius: 5px;
`;

const Button = styled.button`
    padding: 15px 30px;
    margin: 10px;
    background-color: #00AAA9;
    color: #fff;
    border: 2px solid #000;
    border-radius: 5px;
    cursor: pointer;
    font-size: 24px;
    text-transform: uppercase;
    text-shadow: 1px 1px 1px #000;
`;

const CountryInfo = styled.div`
    text-align: center;
    color: #fff;
    background-color: #00AAA9;
    border-radius: 10px;
    padding: 20px;
    font-size: 36px;
    text-shadow: 1px 1px 1px #000;
`;

const LanguageInfo = styled.div`
    text-align: center;
    color: #fff;
    background-color: #00AAA9;
    border-radius: 10px;
    padding: 20px;
    font-size: 36px;
    text-shadow: 1px 1px 1px #000;
`;

const SuccessMessage = styled.div`
    text-align: center;
    color: #fff;
    background-color: #00AAA9;
    border-radius: 10px;
    padding: 20px;
    font-size: 36px;
    text-shadow: 1px 1px 1px #000;
`;

const ErrorMessage = styled.div`
    color: red;
    margin-top: 10px;
`;

const StartOverButton = styled(Button)`
    position: fixed;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    background-color: #800000;
`;

function App() {
    const [step, setStep] = useState('select');
    const [name, setName] = useState('');
    const [capital, setCapital] = useState('');
    const [id, setId] = useState('');
    const [newName, setNewName] = useState('');
    const [newCapital, setNewCapital] = useState('');
    const [country, setCountry] = useState(null);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(null);
    const [languageId, setLanguageId] = useState('');
    const [countries, setCountries] = useState([]);
    const [language, setLanguage] = useState(null);

    const resetState = () => {
        setStep('select');
        setName('');
        setCapital('');
        setId('');
        setNewName('');
        setNewCapital('');
        setCountry(null);
        setSuccess(false);
        setError(null);
        setLanguageId('');
        setCountries([]);
        setLanguage(null);
    };

    const handleAddCountry = async () => {
        try {
            const response = await axios.post('http://localhost:8080/countries', { name, capital });
            console.log(response.data);
            setStep('result');
            setSuccess(true);
            setError(null);
        } catch (error) {
            setError(error.response?.data || 'An error occurred');
        }
    };

    const handleGetCountry = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/countries?name=${name}`);
            console.log(response.data);
            setStep('result');
            setCountry(response.data);
            setError(null);
        } catch (error) {
            setError(error.response?.data || 'An error occurred');
        }
    };

    const handleUpdateCountry = async () => {
        try {
            const response = await axios.put(`http://localhost:8080/countries?name=${name}`, { name: newName, capital: newCapital });
            console.log(response.data);
            setStep('result');
            setSuccess(true);
            setError(null);
        } catch (error) {
            setError(error.response?.data || 'An error occurred');
        }
    };

    const handleDeleteCountry = async () => {
        try {
            const response = await axios.delete(`http://localhost:8080/countries`, { params: { name } });
            console.log(response.data);
            setCountry(null);
            setSuccess(true);
            setError(null);
            setStep('result');
        } catch (error) {
            setError(error.response?.data || 'An error occurred');
        }
    };

    const handleGetLanguage = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/languages?id=${languageId}`);
            console.log(response.data);
            setLanguage(response.data);
            setStep('result');
            setError(null);
        } catch (error) {
            setError(error.response?.data || 'An error occurred');
        }
    };

    return (
        <Container>
            <HeaderContainer>
                <Title>Country Application</Title>
            </HeaderContainer>
            {step === 'select' && (
                <>
                    <ButtonContainer>
                        <Button onClick={() => setStep('add')}>Add Country</Button>
                        <Button onClick={() => setStep('get')}>Get Country</Button>
                        <Button onClick={() => setStep('update')}>Update Country</Button>
                        <Button onClick={() => setStep('delete')}>Delete Country</Button>
                        <Button onClick={() => setStep('getLanguage')}>Get Language</Button>
                    </ButtonContainer>
                </>
            )}
            {step === 'add' && (
                <>
                    <Input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="Enter country name" />
                    <Input type="text" value={capital} onChange={(e) => setCapital(e.target.value)} placeholder="Enter country capital" />
                    <Button onClick={handleAddCountry}>Add Country</Button>
                </>
            )}
            {step === 'get' && (
                <>
                    <Input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="Enter country name" />
                    <Button onClick={handleGetCountry}>Get Country</Button>
                </>
            )}
            {step === 'update' && (
                <>
                    <Input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="Enter country name" />
                    <Input type="text" value={newName} onChange={(e) => setNewName(e.target.value)} placeholder="Enter new country name" />
                    <Input type="text" value={newCapital} onChange={(e) => setNewCapital(e.target.value)} placeholder="Enter new country capital" />
                    <Button onClick={handleUpdateCountry}>Update Country</Button>
                </>
            )}
            {step === 'delete' && (
                <>
                    <Input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="Enter country name" />
                    <Button onClick={handleDeleteCountry}>Delete Country</Button>
                </>
            )}
            {step === 'getLanguage' && (
                <>
                    <Input type="text" value={languageId} onChange={(e) => setLanguageId(e.target.value)} placeholder="Enter language ID" />
                    <Button onClick={handleGetLanguage}>Get Language</Button>
                </>
            )}
            {step === 'result' && (
                <ContentContainer>
                    {success && <SuccessMessage>Country was successfully processed!</SuccessMessage>}
                    {country && (
                        <CountryInfo>
                            <div>Country: {country.name}</div>
                            <div>Capital: {country.capital}</div>
                        </CountryInfo>
                    )}
                    {countries && countries.map((country, index) => (
                        <CountryInfo key={index}>
                            <div>Country: {country.name}</div>
                            <div>Capital: {country.capital}</div>
                        </CountryInfo>
                    ))}
                    {language && (
                        <LanguageInfo>
                            <div>Language: {language.name}</div>
                        </LanguageInfo>
                    )}
                    {error && <ErrorMessage>Error: {error}</ErrorMessage>}
                </ContentContainer>
            )}
            <StartOverButton onClick={resetState}>Start Over</StartOverButton>
        </Container>
    );
}

export default App;
