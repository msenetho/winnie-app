const MAX_TEXT_LENGTH = 250;

export function validateTTSText(rawText) {
    if (typeof rawText !== "string") {
        return {
            isValid: false,
            error: "Text is required",
        };
    }

    const text = rawText.trim();

    if (text.length === 0) {
        return {
            isValid: false,
            error: "Text cannot be empty",
        };
    }

    if (text.length < 2) {
        return {
          isValid: false,
          error: "Text must be at least 2 characters.",
        };
    }

    if (text.length > MAX_TEXT_LENGTH) {
        return {
          isValid: false,
          error: `Text must be ${MAX_TEXT_LENGTH} characters or fewer.`,
        };
    }

    const hasUnsupportedControlCharacter = [...text].some((character) => {
        return character < " " && character !== "\n" && character !== "\t";
    });

    if (hasUnsupportedControlCharacter) {
        return {
            isValid: false,
            error: "Text contains unsupported characters.",
        };
    }

    return {
        isValid: true,
        text,
    };
}